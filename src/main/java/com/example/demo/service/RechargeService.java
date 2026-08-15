package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ExpiringRechargeResponse;
import com.example.demo.dto.RechargeRequest;
import com.example.demo.dto.RechargeResponse;

public interface RechargeService {

    // Create Recharge
    RechargeResponse createRecharge(RechargeRequest request);

    // Get Recharge by ID
    RechargeResponse getRechargeById(Long rechargeId);

    // Get All Recharges (Admin)
    List<RechargeResponse> getAllRecharges();

    // Get Logged-in User Recharges
    List<RechargeResponse> getRechargesByUser(Long userId);

    // Delete Recharge
    String deleteRecharge(Long rechargeId);

    // Get Recharges Expiring Within 3 Days
    List<ExpiringRechargeResponse> getExpiringRecharges();

}