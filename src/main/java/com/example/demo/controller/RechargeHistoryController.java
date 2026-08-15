package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.RechargeHistoryResponse;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.RechargeStatus;
import com.example.demo.service.RechargeHistoryService;


@RestController
@RequestMapping("/rechargehistory")
public class RechargeHistoryController {


    private final RechargeHistoryService rechargeHistoryService;


    public RechargeHistoryController(
            RechargeHistoryService rechargeHistoryService) {

        this.rechargeHistoryService = rechargeHistoryService;
    }



    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{historyId}")
    public ResponseEntity<ApiResponse<RechargeHistoryResponse>> getHistoryById(
            @PathVariable Long historyId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge history fetched successfully",
                        rechargeHistoryService.getHistoryById(historyId),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<RechargeHistoryResponse>>> getHistoryByCustomer(
            @PathVariable Long customerId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Customer recharge history fetched successfully",
                        rechargeHistoryService.getHistoryByCustomer(customerId),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/recharge/{rechargeId}")
    public ResponseEntity<ApiResponse<List<RechargeHistoryResponse>>> getHistoryByRecharge(
            @PathVariable Long rechargeId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge history fetched successfully",
                        rechargeHistoryService.getHistoryByRecharge(rechargeId),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<ApiResponse<List<RechargeHistoryResponse>>> getHistoryByPayment(
            @PathVariable Long paymentId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment history fetched successfully",
                        rechargeHistoryService.getHistoryByPayment(paymentId),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RechargeHistoryResponse>>> getAllHistory() {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "All recharge history fetched successfully",
                        rechargeHistoryService.getAllHistory(),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plan/{planId}")
    public ResponseEntity<ApiResponse<List<RechargeHistoryResponse>>> getHistoryByPlan(
            @PathVariable Long planId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Plan history fetched successfully",
                        rechargeHistoryService.getHistoryByPlan(planId),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/recharge-status/{status}")
    public ResponseEntity<ApiResponse<List<RechargeHistoryResponse>>> getHistoryByRechargeStatus(
            @PathVariable RechargeStatus status) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge status history fetched successfully",
                        rechargeHistoryService.getHistoryByRechargeStatus(status),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/payment-status/{status}")
    public ResponseEntity<ApiResponse<List<RechargeHistoryResponse>>> getHistoryByPaymentStatus(
            @PathVariable PaymentStatus status) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment status history fetched successfully",
                        rechargeHistoryService.getHistoryByPaymentStatus(status),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/between-dates")
    public ResponseEntity<ApiResponse<List<RechargeHistoryResponse>>> getHistoryBetweenDates(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge history fetched successfully",
                        rechargeHistoryService
                                .getHistoryBetweenDates(startDate,endDate),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{historyId}")
    public ResponseEntity<ApiResponse<String>> deleteHistory(
            @PathVariable Long historyId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        rechargeHistoryService.deleteHistory(historyId),
                        null,
                        LocalDateTime.now()
                )
        );
    }

}