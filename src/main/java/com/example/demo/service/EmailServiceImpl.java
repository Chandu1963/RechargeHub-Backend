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

    // 🟢 Verified Permanent Resend API Key & Endpoint
    private final String resendApiKey = "re_TCN9LvLw_8cruipnZXxr7RUgoL9VoKJeg";
    private final String resendApiUrl = "https://api.resend.com/emails";

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            logger.info("Sending OTP email via Resend API to: {}", toEmail);

            String targetEmail = toEmail.trim();

            String jsonInputString = String.format(
                "{\"from\":\"RechargeHub <onboarding@resend.dev>\",\"to\":[\"%s\"],\"subject\":\"%s\",\"html\":\"<h3>%s</h3>\"}",
                targetEmail,
                subject.replace("\"", "\\\""),
                body.replace("\"", "\\\"").replace("\n", "<br/>")
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(resendApiUrl))
                    .header("Authorization", "Bearer " + resendApiKey.trim())
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
