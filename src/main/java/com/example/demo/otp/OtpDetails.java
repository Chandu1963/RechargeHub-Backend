package com.example.demo.otp;

import java.time.LocalDateTime;

public class OtpDetails {
		
		private String otp;
		private LocalDateTime generatedTime;
		private LocalDateTime expiryTime;
		
		public OtpDetails() {
			
		}
		public OtpDetails(String otp, LocalDateTime generatedTime, LocalDateTime expiryTime) {
			this.otp = otp;
			this.generatedTime = generatedTime;
			this.expiryTime = expiryTime;
		}
		public String getOtp() {
			return otp;
		}
		public void setOtp(String otp) {
			this.otp = otp;
		}
		public LocalDateTime getGeneratedTime() {
			return generatedTime;
		}
		public void setGeneratedTime(LocalDateTime generatedTime) {
			this.generatedTime = generatedTime;
		}
		public LocalDateTime getExpiryTime() {
			return expiryTime;
		}
		public void setExpiryTime(LocalDateTime expiryTime) {
			this.expiryTime = expiryTime;
		}
}
