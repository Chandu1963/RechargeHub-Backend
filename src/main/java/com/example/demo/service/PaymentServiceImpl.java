package com.example.demo.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.dto.PaymentResponse;
import com.example.demo.dto.RazorpayOrderRequest;
import com.example.demo.dto.RazorpayOrderResponse;
import com.example.demo.dto.RazorpayVerifyRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Recharge;
import com.example.demo.entity.RechargeHistory;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.RechargeStatus;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.RechargeRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Value("${razorpay.key.id:rzp_test_dummy}")
    private String keyId;

    @Value("${razorpay.key.secret:dummy_secret}")
    private String keySecret;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RechargeRepository rechargeRepository;

    @Autowired
    private RechargeHistoryService rechargeHistoryService;

    @Autowired
    private EmailService emailService;

    @Override
    public RazorpayOrderResponse createRazorpayOrder(RazorpayOrderRequest request) {

        logger.info("Creating Razorpay Order for Recharge ID : {}", request.getRechargeId());

        Recharge recharge = rechargeRepository.findById(request.getRechargeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recharge not found with ID : " + request.getRechargeId()));

        if (paymentRepository.existsByRechargeRechargeId(request.getRechargeId())) {
            logger.warn("Duplicate payment attempt for Recharge ID : {}", request.getRechargeId());
            throw new DuplicateResourceException("Payment already completed for this recharge.");
        }

        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();
            // Razorpay accepts amount in paise (e.g. ₹500 = 50000 paise)
            int amountInPaise = (int) Math.round(recharge.getRechargeAmount() * 100);
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + recharge.getRechargeId());

            Order order = razorpay.orders.create(orderRequest);

            String orderId = order.get("id");
            Integer amount = order.get("amount");
            String currency = order.get("currency");

            logger.info("Razorpay Order Created Successfully. Order ID: {}", orderId);

            return new RazorpayOrderResponse(orderId, amount, currency, keyId);

        } catch (RazorpayException e) {
            logger.error("Failed to create Razorpay Order", e);
            throw new RuntimeException("Error creating Razorpay Order: " + e.getMessage(), e);
        }
    }

    @Override
    public PaymentResponse verifyRazorpayPayment(RazorpayVerifyRequest request) {

        logger.info("Verifying Razorpay Payment for Recharge ID : {}", request.getRechargeId());

        Recharge recharge = rechargeRepository.findById(request.getRechargeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recharge not found with ID : " + request.getRechargeId()));

        if (paymentRepository.existsByRechargeRechargeId(request.getRechargeId())) {
            logger.warn("Duplicate payment verification attempted for Recharge ID : {}", request.getRechargeId());
            throw new DuplicateResourceException("Payment already completed for this recharge.");
        }

        // 1. Verify Signature
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getRazorpayOrderId());
            options.put("razorpay_payment_id", request.getRazorpayPaymentId());
            options.put("razorpay_signature", request.getRazorpaySignature());

            boolean isSignatureValid = Utils.verifyPaymentSignature(options, keySecret);

            if (!isSignatureValid) {
                logger.error("Razorpay signature verification failed for Order ID: {}", request.getRazorpayOrderId());
                throw new RuntimeException("Invalid Razorpay payment signature.");
            }
        } catch (Exception e) {
            logger.error("Error during Razorpay signature verification", e);
            throw new RuntimeException("Razorpay payment verification failed: " + e.getMessage(), e);
        }

        // 2. Save Payment
        Customer customer = recharge.getUser().getCustomer();

        Payment payment = new Payment();
        payment.setRecharge(recharge);
        payment.setAmount(recharge.getRechargeAmount());
        payment.setPaymentMethod(PaymentMethod.UPI); // Correct: Enum constant

        payment.setTransactionId(request.getRazorpayPaymentId());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        Payment savedPayment = paymentRepository.save(payment);

        logger.info("Razorpay Payment verified & saved. Transaction ID : {}", savedPayment.getTransactionId());

        // 3. Update Recharge Status
        recharge.setRechargeStatus(RechargeStatus.SUCCESS);
        rechargeRepository.save(recharge);

        logger.info("Recharge status updated to SUCCESS for Recharge ID : {}", recharge.getRechargeId());

        // 4. Save Recharge History
        RechargeHistory history = new RechargeHistory();
        history.setCustomer(customer);
        history.setRecharge(recharge);
        history.setPayment(savedPayment);
        history.setRechargePlan(recharge.getRechargePlan());
        history.setRechargeAmount(recharge.getRechargeAmount());
        history.setRechargeStatus(recharge.getRechargeStatus());
        history.setPaymentStatus(savedPayment.getPaymentStatus());
        history.setRechargeDate(recharge.getRechargeDate());

        rechargeHistoryService.saveRechargeHistory(history);

        logger.info("Recharge history created successfully.");

        // 5. Send Email Notification
        sendConfirmationEmail(customer, recharge, savedPayment);

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {

        logger.info("Processing payment for Recharge ID : {}",
                paymentRequest.getRechargeId());

        Recharge recharge = rechargeRepository.findById(
                paymentRequest.getRechargeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recharge not found with ID : "
                                + paymentRequest.getRechargeId()));

        if (paymentRepository.existsByRechargeRechargeId(
                paymentRequest.getRechargeId())) {

            logger.warn("Duplicate payment attempted for Recharge ID : {}",
                    paymentRequest.getRechargeId());

            throw new DuplicateResourceException(
                    "Payment already completed for this recharge.");
        }

        Customer customer = recharge.getUser().getCustomer();

        Payment payment = new Payment();

        payment.setRecharge(recharge);
        payment.setAmount(recharge.getRechargeAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        Payment savedPayment = paymentRepository.save(payment);

        logger.info("Payment successful. Transaction ID : {}",
                savedPayment.getTransactionId());

        // Update Recharge Status
        recharge.setRechargeStatus(RechargeStatus.SUCCESS);
        rechargeRepository.save(recharge);

        logger.info("Recharge status updated to SUCCESS for Recharge ID : {}",
                recharge.getRechargeId());

        RechargeHistory history = new RechargeHistory();

        history.setCustomer(customer);
        history.setRecharge(recharge);
        history.setPayment(savedPayment);
        history.setRechargePlan(recharge.getRechargePlan());
        history.setRechargeAmount(recharge.getRechargeAmount());
        history.setRechargeStatus(recharge.getRechargeStatus());
        history.setPaymentStatus(savedPayment.getPaymentStatus());
        history.setRechargeDate(recharge.getRechargeDate());

        rechargeHistoryService.saveRechargeHistory(history);

        logger.info("Recharge history created successfully.");

        sendConfirmationEmail(
                customer,
                recharge,
                savedPayment);

        return mapToResponse(savedPayment);
    }

    private void sendConfirmationEmail(
            Customer customer,
            Recharge recharge,
            Payment payment) {

        try {

            String customerEmail = customer.getEmail();

            if (customerEmail != null && !customerEmail.isBlank()) {

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");

                String body =
                        "Dear " + customer.getCustomerName() + ",\n\n"
                        + "Your recharge has been completed successfully.\n\n"
                        + "Recharge Details\n"
                        + "-----------------------------\n"
                        + "Mobile Number : " + customer.getMobileNumber() + "\n"
                        + "Plan          : " + recharge.getRechargePlan().getPlanName() + "\n"
                        + "Amount        : ₹" + recharge.getRechargeAmount() + "\n"
                        + "Validity      : " + recharge.getPlanValidity() + " Days\n"
                        + "Recharge Date : " + recharge.getRechargeDate().format(formatter) + "\n"
                        + "Expiry Date   : " + recharge.getExpiryDate().format(formatter) + "\n"
                        + "Transaction ID: " + payment.getTransactionId() + "\n\n"
                        + "Thank you for choosing RechargeHub.\n\n"
                        + "Regards,\n"
                        + "RechargeHub Team";

                emailService.sendEmail(
                        customerEmail,
                        "Recharge Successful - RechargeHub",
                        body);

                logger.info("Confirmation email sent successfully to {}",
                        customerEmail);

            } else {

                logger.warn("Customer email not available for Customer ID: {}",
                        customer.getCustomerId());
            }

        } catch (Exception e) {

            logger.error("Failed to send confirmation email.", e);

        }

    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {

        logger.info("Fetching Payment ID : {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with ID : "
                                        + paymentId));

        return mapToResponse(payment);

    }

    @Override
    public List<PaymentResponse> getAllPayments() {

        logger.info("Fetching all payments.");

        List<Payment> payments = paymentRepository.findAll();

        List<PaymentResponse> response = new ArrayList<>();

        for (Payment payment : payments) {

            response.add(mapToResponse(payment));

        }

        logger.info("Total payments found : {}", response.size());

        return response;

    }

    @Override
    public String deletePayment(Long paymentId) {

        logger.info("Deleting Payment ID : {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with ID : " + paymentId));

        paymentRepository.delete(payment);

        logger.info("Payment deleted successfully. Payment ID : {}", paymentId);

        return "Payment Deleted Successfully";
    }

    private PaymentResponse mapToResponse(Payment payment) {

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getPaymentId());

        response.setRechargeId(
                payment.getRecharge().getRechargeId());

        response.setAmount(
                payment.getAmount());

        response.setPaymentMethod(
                payment.getPaymentMethod());

        response.setTransactionId(
                payment.getTransactionId());

        response.setPaymentStatus(
                payment.getPaymentStatus());

        response.setPaymentDate(
                payment.getPaymentDate());

        return response;
    }

}
