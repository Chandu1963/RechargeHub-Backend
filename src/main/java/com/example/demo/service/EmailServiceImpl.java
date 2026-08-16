package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmailServiceImpl.class);

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ Verified & Active Brevo v3 HTTPS API Key
    private final String brevoApiKey = "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";
    private final String brevoApiUrl = "https://api.brevo.com/v3/smtp/email";

    @Override
    public void sendEmail(
            String toEmail,
            String subject,
            String body) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("accept", "application/json");
            headers.set("api-key", brevoApiKey.trim());

            Map<String, Object> sender = new HashMap<>();
            sender.put("name", "RechargeHub");
            sender.put("email", "edubillichandu768@gmail.com");

            Map<String, Object> recipient = new HashMap<>();
            recipient.put("email", toEmail.trim());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("sender", sender);
            requestBody.put("to", List.of(recipient));
            requestBody.put("subject", subject);
            requestBody.put("textContent", body);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            restTemplate.postForEntity(brevoApiUrl, entity, String.class);

            logger.info("OTP Email sent successfully via Brevo HTTPS API to: {}", toEmail);

        } catch (Exception e) {
            logger.error("Failed to send email via Brevo API to: {}. Error: {}", toEmail, e.getMessage());
        }
    }
}
