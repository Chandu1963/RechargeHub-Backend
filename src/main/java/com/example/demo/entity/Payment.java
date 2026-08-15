package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;


@Entity
@Table(name = "payments")
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;



    @OneToOne
    @JoinColumn(name = "recharge_id",
                nullable = false,
                unique = true)
    private Recharge recharge;


    @Column(nullable = false)
    private Double amount;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;



    @Column(nullable = false, unique = true)
    private String transactionId;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;



    private LocalDateTime paymentDate;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;




    public Payment() {

    }



    public Payment(Long paymentId,
            Recharge recharge,
            Double amount,
            PaymentMethod paymentMethod,
            String transactionId,
            PaymentStatus paymentStatus,
            LocalDateTime paymentDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.paymentId = paymentId;
        this.recharge = recharge;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }




    public Long getPaymentId() {
        return paymentId;
    }


    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }


    public Recharge getRecharge() {
        return recharge;
    }


    public void setRecharge(Recharge recharge) {
        this.recharge = recharge;
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




    @PrePersist
    public void onCreate() {


        paymentDate = LocalDateTime.now();

        createdAt = LocalDateTime.now();

        updatedAt = LocalDateTime.now();


        if(paymentStatus == null) {

            paymentStatus = PaymentStatus.PENDING;

        }

    }



    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }


}