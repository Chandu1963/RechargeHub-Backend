package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PaymentRequest;
import com.example.demo.dto.PaymentResponse;
import com.example.demo.dto.RazorpayOrderRequest;
import com.example.demo.dto.RazorpayOrderResponse;
import com.example.demo.dto.RazorpayVerifyRequest;
import com.example.demo.service.PaymentService;


@RestController
@RequestMapping("/payments")
public class PaymentController {


    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }



    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create-order")
    public ResponseEntity<ApiResponse<RazorpayOrderResponse>> createRazorpayOrder(
            @Valid @RequestBody RazorpayOrderRequest request) {

        RazorpayOrderResponse response =
                paymentService.createRazorpayOrder(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Razorpay order created successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('USER')")
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<PaymentResponse>> verifyRazorpayPayment(
            @Valid @RequestBody RazorpayVerifyRequest request) {

        PaymentResponse response =
                paymentService.verifyRazorpayPayment(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment verified and completed successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<PaymentResponse>> makePayment(
            @Valid @RequestBody PaymentRequest paymentRequest) {


        PaymentResponse response =
                paymentService.makePayment(paymentRequest);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment completed successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable Long paymentId) {


        PaymentResponse response =
                paymentService.getPaymentById(paymentId);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment fetched successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {


        List<PaymentResponse> response =
                paymentService.getAllPayments();


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payments fetched successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<String>> deletePayment(
            @PathVariable Long paymentId) {


        String response =
                paymentService.deletePayment(paymentId);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        LocalDateTime.now()
                )
        );
    }

}
