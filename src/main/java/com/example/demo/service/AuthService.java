package com.example.demo.service;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.VerifyOtpRequest;

public interface AuthService {

    String sendOtp(LoginRequest request);

    JwtResponse verifyOtp(VerifyOtpRequest request);

}