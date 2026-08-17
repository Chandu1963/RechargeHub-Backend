package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            logger.info("Sending OTP email from edubillichandu768@gmail.com to: {}", toEmail);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("RechargeHub <edubillichandu768@gmail.com>");
            helper.setTo(toEmail.trim());
            helper.setSubject(subject);
            helper.setText("<h3>" + body + "</h3>", true);

            mailSender.send(message);

            logger.info("OTP Email sent successfully from edubillichandu768@gmail.com to: {}", toEmail);

        } catch (Exception e) {
            logger.error("Error sending email via Gmail SMTP: {}", e.getMessage(), e);
        }
    }
}
