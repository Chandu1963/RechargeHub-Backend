package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.RechargeStatus;

public class RechargeHistoryResponse {

    private Long historyId;

    private Long customerId;

    private String customerName;

    private String mobileNumber;

    private Long rechargeId;

    private Long paymentId;

    private Long planId;

    private String planName;

    private Double rechargeAmount;

    private RechargeStatus rechargeStatus;

    private PaymentStatus paymentStatus;

    private LocalDateTime rechargeDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public RechargeHistoryResponse() {

    }

    public RechargeHistoryResponse(
            Long historyId,
            Long customerId,
            String customerName,
            String mobileNumber,
            Long rechargeId,
            Long paymentId,
            Long planId,
            String planName,
            Double rechargeAmount,
            RechargeStatus rechargeStatus,
            PaymentStatus paymentStatus,
            LocalDateTime rechargeDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.historyId = historyId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.rechargeId = rechargeId;
        this.paymentId = paymentId;
        this.planId = planId;
        this.planName = planName;
        this.rechargeAmount = rechargeAmount;
        this.rechargeStatus = rechargeStatus;
        this.paymentStatus = paymentStatus;
        this.rechargeDate = rechargeDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public RechargeStatus getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(RechargeStatus rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(LocalDateTime rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}