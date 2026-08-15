package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CustomerRegistrationRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.VerifyOtpRequest;
import com.example.demo.entity.Customer;
import com.example.demo.dto.CustomerRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final CustomerService customerService;

    public AuthController(
            AuthService authService,
            CustomerService customerService) {

        this.authService = authService;
        this.customerService = customerService;
    }

    // ============================================================
    // CUSTOMER REGISTRATION
    // PUBLIC
    // ============================================================
    @PostMapping("/register-customer")
    public ResponseEntity<ApiResponse<Customer>> registerCustomer(
            @Valid @RequestBody CustomerRegistrationRequest request) {

        CustomerRequest customerRequest = new CustomerRequest();

        customerRequest.setCustomerName(request.getCustomerName());
        customerRequest.setMobileNumber(request.getMobileNumber());
        customerRequest.setEmail(request.getEmail());
        customerRequest.setCircle(request.getCircle());

        // Status is intentionally not set here.
        // Customer entity @PrePersist will set ACTIVE by default.

        Customer customer =
                customerService.registerCustomer(customerRequest);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Customer registered successfully",
                        customer,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // USER / ADMIN LOGIN
    // ============================================================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "OTP sent successfully",
                        authService.sendOtp(request),
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // VERIFY OTP
    // ============================================================
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<JwtResponse>> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        authService.verifyOtp(request),
                        LocalDateTime.now()
                )
        );
    }
}
