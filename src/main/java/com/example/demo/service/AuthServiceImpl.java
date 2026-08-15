package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.VerifyOtpRequest;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Customer;
import com.example.demo.entity.User;
import com.example.demo.enums.AdminStatus;
import com.example.demo.enums.CustomerStatus;
import com.example.demo.enums.UserRole;
import com.example.demo.enums.UserStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final AdminRepository adminRepository;
    private final OtpService otpService;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthServiceImpl(
            CustomerRepository customerRepository,
            UserService userService,
            AdminRepository adminRepository,
            OtpService otpService,
            JwtUtil jwtUtil,
            EmailService emailService) {

        this.customerRepository = customerRepository;
        this.userService = userService;
        this.adminRepository = adminRepository;
        this.otpService = otpService;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Override
    public String sendOtp(LoginRequest request) {

        String mobileNumber = request.getMobileNumber();

        // ==========================
        // USER LOGIN
        // ==========================
        if ("USER".equalsIgnoreCase(request.getLoginType())) {

            Customer customer = customerRepository
                    .findByMobileNumber(mobileNumber)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Customer not registered"));

            // Check Customer Status
            if (customer.getStatus() != CustomerStatus.ACTIVE) {

                throw new RuntimeException(
                        "Customer inactive");
            }

            // Find User linked with Customer
            User user = userService
                    .getUserByCustomer(customer)
                    .orElse(null);

            // If User does not exist, create User
            if (user == null) {

                userService.createUser(customer);

            }
            // User exists - check User Status
            else if (user.getStatus() == UserStatus.INACTIVE) {

                throw new RuntimeException(
                        "User is inactive");

            }
            else if (user.getStatus() == UserStatus.BLOCKED) {

                throw new RuntimeException(
                        "User is blocked");
            }

            // Generate OTP
            String otp = otpService.generateOtp(mobileNumber);

            String subject = "RechargeHub Login OTP";

            String body = String.format(
                    """
                    Hello %s,

                    Your One-Time Password (OTP) for RechargeHub login is:

                    %s

                    This OTP is valid for 5 minutes.

                    Please do not share this OTP with anyone.

                    Regards,
                    RechargeHub Team
                    """,
                    customer.getCustomerName(),
                    otp
            );

            // Send OTP to registered email
            emailService.sendEmail(
                    customer.getEmail(),
                    subject,
                    body
            );

            return "OTP sent successfully to your registered email.";
        }


        // ==========================
        // ADMIN LOGIN
        // ==========================
        if ("ADMIN".equalsIgnoreCase(request.getLoginType())) {

            Admin admin = adminRepository
                    .findByMobileNumber(mobileNumber)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Admin not found"));

            // Check Admin Status
            if (admin.getStatus() != AdminStatus.ACTIVE) {

                throw new RuntimeException(
                        "Admin inactive");
            }

            // Generate OTP
            String otp = otpService.generateOtp(mobileNumber);

            String subject = "RechargeHub Admin Login OTP";

            String body = String.format(
                    """
                    Hello Admin,

                    Your One-Time Password (OTP) for RechargeHub Admin Login is:

                    %s

                    This OTP is valid for 5 minutes.

                    Please do not share this OTP with anyone.

                    Regards,
                    RechargeHub Team
                    """,
                    otp
            );

            // Send OTP to Admin email
            emailService.sendEmail(
                    admin.getEmail(),
                    subject,
                    body
            );

            return "OTP sent successfully to your registered email.";
        }


        // ==========================
        // INVALID LOGIN TYPE
        // ==========================
        throw new RuntimeException(
                "Invalid login type");
    }


    @Override
    public JwtResponse verifyOtp(VerifyOtpRequest request) {

        // Verify OTP
        if (!otpService.verifyOtp(
                request.getMobileNumber(),
                request.getOtp())) {

            throw new RuntimeException(
                    "Invalid or expired OTP");
        }


        // ==========================
        // CHECK USER LOGIN
        // ==========================
        Customer customer = customerRepository
                .findByMobileNumber(request.getMobileNumber())
                .orElse(null);

        if (customer != null) {

            User user = userService
                    .getUserByCustomer(customer)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "User not found"));

            return new JwtResponse(
                    jwtUtil.generateToken(user),
                    "ROLE_" + user.getRole().name(),
                    "User login successful",
                    customer.getCustomerId()
            );
        }


        // ==========================
        // CHECK ADMIN LOGIN
        // ==========================
        Admin admin = adminRepository
                .findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin not found"));

        return new JwtResponse(
                jwtUtil.generateToken(admin),
                "ROLE_" + UserRole.ADMIN.name(),
                "Admin login successful",
                null
        );
    }
}