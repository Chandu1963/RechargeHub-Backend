package com.example.demo.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmailServiceImpl.class);

    // 🟢 Verified Resend API Key & Endpoint
    private final String resendApiKey = "re_WVaMhRFS_5DrS9YiHf2JCBSaLcNWn2cMc";
    private final String resendApiUrl = "https://api.resend.com/emails";

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        HttpURLConnection conn = null;
        try {
            logger.info("Sending OTP email to {} via Resend API...", toEmail);

            URL url = new URL(resendApiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + resendApiKey.trim());
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = String.format(
                "{\"from\":\"RechargeHub <onboarding@resend.dev>\",\"to\":[\"%s\"],\"subject\":\"%s\",\"html\":\"<h3>%s</h3>\"}",
                toEmail.trim(),
                subject.replace("\"", "\\\""),
                body.replace("\"", "\\\"").replace("\n", "<br/>")
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            logger.info("Resend API Response Code: {}", responseCode);

            InputStream is = (responseCode >= 200 && responseCode < 300) 
                    ? conn.getInputStream() 
                    : conn.getErrorStream();

            if (is != null) {
                String responseBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                logger.info("Resend API Response Body: {}", responseBody);
            }

        } catch (Exception e) {
            logger.error("Error sending email via Resend API: {}", e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
