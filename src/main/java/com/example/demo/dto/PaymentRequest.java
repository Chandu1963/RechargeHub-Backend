package com.example.demo.dto;

import com.example.demo.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;

public class PaymentRequest {

    @NotNull(message = "Recharge ID is required")
    private Long rechargeId;

    @NotNull(message = "Payment Method is required")
    private PaymentMethod paymentMethod;

    public PaymentRequest() {

    }

    public PaymentRequest(Long rechargeId, PaymentMethod paymentMethod) {
        this.rechargeId = rechargeId;
        this.paymentMethod = paymentMethod;
    }

    public Long getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}