package com.example.demo.service;

public interface OtpService {
	
	String generateOtp(String mobileNumber);

    boolean verifyOtp(String mobileNumber, String enteredOtp);
		

}
