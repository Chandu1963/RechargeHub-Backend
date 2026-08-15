package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.RechargeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "recharge_history")
public class RechargeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "recharge_id", nullable = false)
    private Recharge recharge;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private RechargePlan rechargePlan;

    private Double rechargeAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RechargeStatus rechargeStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private LocalDateTime rechargeDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public RechargeHistory() {

    }


    public RechargeHistory(Long historyId, Customer customer, Recharge recharge,
            Payment payment, RechargePlan rechargePlan,
            Double rechargeAmount, RechargeStatus rechargeStatus,
            PaymentStatus paymentStatus, LocalDateTime rechargeDate,
            LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.historyId = historyId;
        this.customer = customer;
        this.recharge = recharge;
        this.payment = payment;
        this.rechargePlan = rechargePlan;
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


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Recharge getRecharge() {
        return recharge;
    }

    public void setRecharge(Recharge recharge) {
        this.recharge = recharge;
    }


    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    public RechargePlan getRechargePlan() {
        return rechargePlan;
    }

    public void setRechargePlan(RechargePlan rechargePlan) {
        this.rechargePlan = rechargePlan;
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


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if(rechargeDate == null) {
            rechargeDate = LocalDateTime.now();
        }
    }


    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();
    }

}