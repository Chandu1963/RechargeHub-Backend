package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RechargeHistory;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.RechargeStatus;

@Repository
public interface RechargeHistoryRepository
        extends JpaRepository<RechargeHistory, Long> {

    // Find all history by customer
    List<RechargeHistory> findByCustomerCustomerId(Long customerId);

    // Find history by recharge
    List<RechargeHistory> findByRechargeRechargeId(Long rechargeId);

    // Find history by payment
    List<RechargeHistory> findByPaymentPaymentId(Long paymentId);

    // Find history by recharge plan
    List<RechargeHistory> findByRechargePlanPlanId(Long planId);

    // Find history by recharge status
    List<RechargeHistory> findByRechargeStatus(RechargeStatus rechargeStatus);

    // Find history by payment status
    List<RechargeHistory> findByPaymentStatus(PaymentStatus paymentStatus);

    // Find history between two recharge dates
    List<RechargeHistory> findByRechargeDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

}