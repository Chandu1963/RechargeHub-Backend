package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Recharge;
import com.example.demo.enums.RechargeStatus;

@Repository
public interface RechargeRepository extends JpaRepository<Recharge, Long> {

    // Expiring recharges
    List<Recharge> findByExpiryDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    // Get all recharges of a user
    List<Recharge> findByUserUserId(Long userId);

    // Get recharges by status
    List<Recharge> findByRechargeStatus(RechargeStatus rechargeStatus);

}