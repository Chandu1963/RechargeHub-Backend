package com.example.demo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String SENDGRID_URL = "https://api.sendgrid.com/v3/mail/send";
    private static final String SENDER_EMAIL = "edubillichandu768@gmail.com";
    private static final String SENDER_NAME = "RechargeHub";

    @Value("${SENDGRID_API_KEY:}")
    private String sendGridApiKey;

    private final HttpClient httpClient;

    public EmailServiceImpl() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        if (sendGridApiKey == null || sendGridApiKey.isBlank()) {
            logger.error("SENDGRID_API_KEY is missing from environment variables!");
            throw new IllegalStateException("SendGrid API Key is not configured on server.");
        }

        try {
            String jsonPayload = buildSendGridPayload(toEmail, subject, body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SENDGRID_URL))
                    .header("Authorization", "Bearer " + sendGridApiKey.trim())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 202) {
                logger.info("Email queued successfully via SendGrid REST API to {}", toEmail);
            } else {
                logger.error("SendGrid API failed. Status: {}, Body: {}", response.statusCode(), response.body());
                throw new RuntimeException("SendGrid API Error [" + response.statusCode() + "]: " + response.body());
            }

        } catch (Exception e) {
            logger.error("Error sending email to {}", toEmail, e);
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    
    public void sendOtpEmail(String toEmail, String otp) {
        String subject = "Your RechargeHub Verification Code: " + otp;
        String htmlBody = buildOtpEmailHtml(otp);
        sendEmail(toEmail, subject, htmlBody);
    }

    private String buildSendGridPayload(String toEmail, String subject, String contentValue) {
        String escapedSubject = escapeJson(subject);
        String escapedContent = escapeJson(contentValue);

        // Auto-detect HTML vs Plain Text
        String contentType = (contentValue != null && (contentValue.trim().toLowerCase().startsWith("<!doctype html") 
                                                    || contentValue.contains("<html"))) 
                             ? "text/html" : "text/plain";

        return "{"
                + "\"personalizations\":[{"
                + "\"to\":[{\"email\":\"" + toEmail + "\"}]"
                + "}],\"from\":{"
                + "\"email\":\"" + SENDER_EMAIL + "\","
                + "\"name\":\"" + SENDER_NAME + "\""
                + "},"
                + "\"subject\":\"" + escapedSubject + "\","
                + "\"content\":[{"
                + "\"type\":\"" + contentType + "\","
                + "\"value\":\"" + escapedContent + "\""
                + "}]"
                + "}";
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }

    private String buildOtpEmailHtml(String otp) {
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head><meta charset=\"UTF-8\"><style>"
                + "body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #0f172a; margin: 0; padding: 24px; color: #f8fafc; }"
                + ".card { max-width: 480px; margin: 0 auto; background: #1e293b; border-radius: 16px; padding: 32px; border: 1px solid #334155; box-shadow: 0 10px 25px rgba(0,0,0,0.5); }"
                + ".brand { text-align: center; font-size: 24px; font-weight: 800; color: #6366f1; letter-spacing: 1px; margin-bottom: 24px; }"
                + ".title { text-align: center; font-size: 20px; font-weight: 600; color: #ffffff; margin-bottom: 8px; }"
                + ".subtitle { text-align: center; color: #94a3b8; font-size: 14px; margin-bottom: 28px; line-height: 1.5; }"
                + ".otp-container { background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%); border-radius: 12px; padding: 20px; text-align: center; margin-bottom: 28px; box-shadow: 0 8px 16px rgba(79,70,229,0.3); }"
                + ".otp-code { font-size: 38px; font-weight: 800; letter-spacing: 8px; color: #ffffff; }"
                + ".footer { text-align: center; color: #64748b; font-size: 12px; margin-top: 24px; border-top: 1px solid #334155; padding-top: 16px; }"
                + "</style></head>"
                + "<body>"
                + "<div class=\"card\">"
                + "<div class=\"brand\">⚡ RechargeHub</div>"
                + "<div class=\"title\">Login Verification Code</div>"
                + "<div class=\"subtitle\">Use the 6-digit OTP below to log in. This code is valid for 5 minutes.</div>"
                + "<div class=\"otp-container\"><span class=\"otp-code\">" + otp + "</span></div>"
                + "<div class=\"subtitle\">If you did not request this OTP, please ignore this message.</div>"
                + "<div class=\"footer\">&copy; 2026 RechargeHub. All rights reserved.</div>"
                + "</div>"
                + "</body></html>";
    }
}
