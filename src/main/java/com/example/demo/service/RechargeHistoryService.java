package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.dto.RechargeHistoryResponse;
import com.example.demo.entity.RechargeHistory;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.RechargeStatus;

public interface RechargeHistoryService {

    // Used internally by PaymentService after successful payment
    RechargeHistory saveRechargeHistory(RechargeHistory rechargeHistory);

    // Get Recharge History by ID
    RechargeHistoryResponse getHistoryById(Long historyId);

    // Get All Recharge History
    List<RechargeHistoryResponse> getAllHistory();

    // Get Recharge History by Customer
    List<RechargeHistoryResponse> getHistoryByCustomer(Long customerId);

    // Get Recharge History by Recharge
    List<RechargeHistoryResponse> getHistoryByRecharge(Long rechargeId);

    // Get Recharge History by Payment
    List<RechargeHistoryResponse> getHistoryByPayment(Long paymentId);

    // Get Recharge History by Recharge Plan
    List<RechargeHistoryResponse> getHistoryByPlan(Long planId);

    // Get Recharge History by Recharge Status
    List<RechargeHistoryResponse> getHistoryByRechargeStatus(
            RechargeStatus rechargeStatus);

    // Get Recharge History by Payment Status
    List<RechargeHistoryResponse> getHistoryByPaymentStatus(
            PaymentStatus paymentStatus);

    // Get Recharge History Between Dates
    List<RechargeHistoryResponse> getHistoryBetweenDates(
            LocalDateTime startDate,
            LocalDateTime endDate);

    // Delete Recharge History
    String deleteHistory(Long historyId);

}