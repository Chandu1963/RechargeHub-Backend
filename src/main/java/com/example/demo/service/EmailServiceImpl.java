package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmail(
            String toEmail,
            String subject,
            String body) {

        try {

            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);

            logger.info(
                    "Email sent successfully to : {}",
                    toEmail);

        } catch (Exception e) {

            // ✅ Log the error but DO NOT THROW 'e' so login continues cleanly
            logger.error(
                    "SMTP Connection blocked or failed for : {}. Error: {}",
                    toEmail,
                    e.getMessage());

            System.out.println("=========================================");
            System.out.println("NOTIFICATION BODY:\n" + body);
            System.out.println("=========================================");
        }

    }

}
