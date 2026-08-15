package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginRequest {

    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number"
    )
    private String mobileNumber;

    @NotBlank(message = "Login type is required")
    @Pattern(
            regexp = "^(USER|ADMIN)$",
            message = "Login type must be USER or ADMIN"
    )
    private String loginType;

    public LoginRequest() {
    }

    public LoginRequest(String mobileNumber, String loginType) {
        this.mobileNumber = mobileNumber;
        this.loginType = loginType;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}