package com.example.demo.dto;

import java.time.LocalDateTime;

public class ExpiringRechargeResponse {


    private Long rechargeId;

    private String customerName;

    private String mobileNumber;

    private String planName;

    private Double rechargeAmount;

    private LocalDateTime expiryDate;

    private Long daysRemaining;



    public ExpiringRechargeResponse() {

    }



    public ExpiringRechargeResponse(Long rechargeId,
            String customerName,
            String mobileNumber,
            String planName,
            Double rechargeAmount,
            LocalDateTime expiryDate,
            Long daysRemaining) {

        this.rechargeId = rechargeId;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.planName = planName;
        this.rechargeAmount = rechargeAmount;
        this.expiryDate = expiryDate;
        this.daysRemaining = daysRemaining;

    }



    public Long getRechargeId() {
        return rechargeId;
    }


    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
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


    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }


    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }


    public Long getDaysRemaining() {
        return daysRemaining;
    }


    public void setDaysRemaining(Long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

}