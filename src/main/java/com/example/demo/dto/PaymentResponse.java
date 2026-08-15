package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;

public class PaymentResponse {

    private Long paymentId;

    private Long rechargeId;

    private Double amount;

    private PaymentMethod paymentMethod;

    private String transactionId;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;


    public PaymentResponse() {

    }


    public PaymentResponse(Long paymentId,
                           Long rechargeId,
                           Double amount,
                           PaymentMethod paymentMethod,
                           String transactionId,
                           PaymentStatus paymentStatus,
                           LocalDateTime paymentDate) {

        this.paymentId = paymentId;
        this.rechargeId = rechargeId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }


    public Long getPaymentId() {
        return paymentId;
    }


    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }


    public Long getRechargeId() {
        return rechargeId;
    }


    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }


    public Double getAmount() {
        return amount;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }


    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public String getTransactionId() {
        return transactionId;
    }


    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }


    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }


    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

}