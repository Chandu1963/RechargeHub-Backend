
package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.otp.OtpDetails;
import com.example.demo.otp.OtpStore;

@Service
public class OtpServiceImpl implements OtpService {

    private static final Logger logger =
            LoggerFactory.getLogger(OtpServiceImpl.class);

    @Autowired
    private OtpStore otpStore;

    @Override
    public String generateOtp(String mobileNumber) {

        String otp = String.format(
                "%06d",
                ThreadLocalRandom.current().nextInt(1000000)
        );

        // DEVELOPMENT ONLY - REMOVE BEFORE PRODUCTION
        System.out.println("=================================");
        System.out.println("Generated OTP : " + otp);
        System.out.println("Mobile Number : " + mobileNumber);
        System.out.println("=================================");

        LocalDateTime generatedTime =
                LocalDateTime.now();

        LocalDateTime expiryTime =
                generatedTime.plusMinutes(5);

        OtpDetails otpDetails =
                new OtpDetails(
                        otp,
                        generatedTime,
                        expiryTime
                );

        otpStore.saveOtp(
                mobileNumber,
                otpDetails
        );

        logger.info(
                "OTP generated successfully for mobile number : {}",
                mobileNumber
        );

        logger.info(
                "OTP Expiry Time : {}",
                expiryTime
        );

        // OTP is returned to AuthServiceImpl
        // so it can be emailed to the customer.
        return otp;
    }

    @Override
    public boolean verifyOtp(
            String mobileNumber,
            String enteredOtp) {

        OtpDetails otpDetails =
                otpStore.getOtpDetails(mobileNumber);

        if (otpDetails == null) {

            logger.warn(
                    "OTP verification failed. OTP not found for mobile number : {}",
                    mobileNumber
            );

            return false;
        }

        if (LocalDateTime.now()
                .isAfter(otpDetails.getExpiryTime())) {

            otpStore.removeOtp(mobileNumber);

            logger.warn(
                    "OTP expired for mobile number : {}",
                    mobileNumber
            );

            return false;
        }

        if (!otpDetails.getOtp()
                .equals(enteredOtp)) {

            logger.warn(
                    "Invalid OTP entered for mobile number : {}",
                    mobileNumber
            );

            return false;
        }

        otpStore.removeOtp(mobileNumber);

        logger.info(
                "OTP verified successfully for mobile number : {}",
                mobileNumber
        );

        return true;
    }
}

