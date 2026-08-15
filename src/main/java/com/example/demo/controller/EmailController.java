package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.service.EmailService;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;


    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> sendTestEmail() {


        emailService.sendEmail(
                "edubillichandu768@gmail.com",
                "RechargeHub Test Email",
                """
                Hello Chandu,

                Congratulations!

                Your Email Service is working successfully.

                This is a test email sent from RechargeHub.

                Regards,
                RechargeHub Team
                """
        );


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Test Email Sent Successfully",
                        null,
                        LocalDateTime.now()
                )
        );
    }

}