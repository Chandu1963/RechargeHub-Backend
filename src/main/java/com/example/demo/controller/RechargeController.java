package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.ExpiringRechargeResponse;
import com.example.demo.dto.RechargeRequest;
import com.example.demo.dto.RechargeResponse;
import com.example.demo.service.RechargeService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/recharges")
@Validated
public class RechargeController {



    private final RechargeService rechargeService;



    public RechargeController(RechargeService rechargeService) {

        this.rechargeService = rechargeService;
    }


    // ==========================
    // USER
    // ==========================



    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<RechargeResponse>> createRecharge(
            @Valid @RequestBody RechargeRequest request) {


        RechargeResponse response =
                rechargeService.createRecharge(request);



        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge created successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }





    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{rechargeId}")
    public ResponseEntity<ApiResponse<RechargeResponse>> getRechargeById(
            @PathVariable Long rechargeId) {


        RechargeResponse response =
                rechargeService.getRechargeById(rechargeId);



        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge fetched successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }





    // ==========================
    // ADMIN
    // ==========================



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RechargeResponse>>> getAllRecharges() {


        List<RechargeResponse> response =
                rechargeService.getAllRecharges();



        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharges fetched successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }





    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{rechargeId}")
    public ResponseEntity<ApiResponse<String>> deleteRecharge(
            @PathVariable Long rechargeId) {


        String response =
                rechargeService.deleteRecharge(rechargeId);



        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        LocalDateTime.now()
                )
        );
    }





    // ==========================================
    // ADMIN - EXPIRING RECHARGES
    // ==========================================



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/expiring-soon")
    public ResponseEntity<ApiResponse<List<ExpiringRechargeResponse>>> getExpiringRecharges() {


        List<ExpiringRechargeResponse> response =
                rechargeService.getExpiringRecharges();



        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Expiring recharges fetched successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }

}