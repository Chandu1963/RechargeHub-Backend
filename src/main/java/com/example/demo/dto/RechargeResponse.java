package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.enums.RechargeStatus;
import com.example.demo.enums.RechargeType;

public class RechargeResponse {


    private Long rechargeId;

    private Long userId;

    private String customerName;

    private String mobileNumber;

    private Long planId;

    private String planName;

    private Double rechargeAmount;

    private Integer planValidity;

    private LocalDateTime rechargeDate;

    private LocalDateTime expiryDate;

    private RechargeStatus rechargeStatus;

    private RechargeType rechargeType;



    public RechargeResponse() {

    }



    public RechargeResponse(Long rechargeId,
            Long userId,
            String customerName,
            String mobileNumber,
            Long planId,
            String planName,
            Double rechargeAmount,
            Integer planValidity,
            LocalDateTime rechargeDate,
            LocalDateTime expiryDate,
            RechargeStatus rechargeStatus,
            RechargeType rechargeType) {

        this.rechargeId = rechargeId;
        this.userId = userId;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.planId = planId;
        this.planName = planName;
        this.rechargeAmount = rechargeAmount;
        this.planValidity = planValidity;
        this.rechargeDate = rechargeDate;
        this.expiryDate = expiryDate;
        this.rechargeStatus = rechargeStatus;
        this.rechargeType = rechargeType;

    }



    public Long getRechargeId() {
        return rechargeId;
    }


    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }


    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
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


    public Integer getPlanValidity() {
        return planValidity;
    }


    public void setPlanValidity(Integer planValidity) {
        this.planValidity = planValidity;
    }


    public LocalDateTime getRechargeDate() {
        return rechargeDate;
    }


    public void setRechargeDate(LocalDateTime rechargeDate) {
        this.rechargeDate = rechargeDate;
    }


    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }


    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }


    public RechargeStatus getRechargeStatus() {
        return rechargeStatus;
    }


    public void setRechargeStatus(RechargeStatus rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }


    public RechargeType getRechargeType() {
        return rechargeType;
    }


    public void setRechargeType(RechargeType rechargeType) {
        this.rechargeType = rechargeType;
    }

}