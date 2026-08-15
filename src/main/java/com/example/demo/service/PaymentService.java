package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.dto.PaymentResponse;
import com.example.demo.dto.RazorpayOrderRequest;
import com.example.demo.dto.RazorpayOrderResponse;
import com.example.demo.dto.RazorpayVerifyRequest;

public interface PaymentService {

    RazorpayOrderResponse createRazorpayOrder(RazorpayOrderRequest request);

    PaymentResponse verifyRazorpayPayment(RazorpayVerifyRequest request);

    PaymentResponse makePayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentById(Long paymentId);

    List<PaymentResponse> getAllPayments();

    String deletePayment(Long paymentId);

}
