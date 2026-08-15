package com.example.demo.otp;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;


@Component
public class OtpStore {
	
	private final ConcurrentHashMap<String,OtpDetails> otpMap = new ConcurrentHashMap<>();
	
	public void saveOtp(String mobileNumber,OtpDetails otpDetails) {
		otpMap.put(mobileNumber, otpDetails);
	}
	
	public OtpDetails getOtpDetails(String mobileNumber) {
		return otpMap.get(mobileNumber);
	}
	
	public void removeOtp(String mobileNumber) {
		otpMap.remove(mobileNumber);
	}

}
