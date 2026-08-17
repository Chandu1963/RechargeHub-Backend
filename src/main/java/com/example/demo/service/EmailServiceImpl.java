package com.example.demo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmailServiceImpl.class);

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String resendApiUrl = "https://api.resend.com/emails";

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            String apiKey = System.getenv("RESEND_API_KEY");
            if (apiKey == null || apiKey.trim().isEmpty()) {
                logger.error("RESEND_API_KEY environment variable is missing on Render!");
                return;
            }

            logger.info("Sending OTP email via Resend API to: {}", toEmail);

            String jsonInputString = String.format(
                "{\"from\":\"RechargeHub <onboarding@resend.dev>\",\"to\":[\"%s\"],\"subject\":\"%s\",\"html\":\"<h3>%s</h3>\"}",
                toEmail.trim(),
                subject.replace("\"", "\\\""),
                body.replace("\"", "\\\"").replace("\n", "<br/>")
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(resendApiUrl))
                    .header("Authorization", "Bearer " + apiKey.trim())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("Resend API Response Code: {}", response.statusCode());
            logger.info("Resend API Response Body: {}", response.body());

        } catch (Exception e) {
            logger.error("Error sending email via Resend API: {}", e.getMessage(), e);
        }
    }
}
