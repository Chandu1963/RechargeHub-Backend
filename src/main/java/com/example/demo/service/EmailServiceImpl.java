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

    // 🟢 100% Active & Verified Brevo API Key
    private final String brevoApiKey = "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";

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        HttpURLConnection conn = null;
        try {
            logger.info("Sending OTP email to {} via Brevo API...", toEmail);

            URL url = new URL("https://api.brevo.com/v3/smtp/email");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("api-key", brevoApiKey.trim());

            String jsonInputString = String.format(
                "{\"sender\":{\"name\":\"RechargeHub\",\"email\":\"edubillichandu768@gmail.com\"},\"to\":[{\"email\":\"%s\"}],\"subject\":\"%s\",\"textContent\":\"%s\"}",
                toEmail.trim(),
                subject.replace("\"", "\\\""),
                body.replace("\"", "\\\"").replace("\n", "\\n")
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            logger.info("Brevo API HTTP Response Code: {}", responseCode);

            InputStream is = (responseCode >= 200 && responseCode < 300) 
                    ? conn.getInputStream() 
                    : conn.getErrorStream();

            if (is != null) {
                String responseBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                logger.info("Brevo API Response Body: {}", responseBody);
            }

        } catch (Exception e) {
            logger.error("Error sending email via Brevo API: {}", e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
