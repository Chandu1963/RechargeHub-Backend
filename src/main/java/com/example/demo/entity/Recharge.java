package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.enums.RechargeStatus;
import com.example.demo.enums.RechargeType;

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
@Table(name = "recharges")
public class Recharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rechargeId;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private RechargePlan rechargePlan;


    @Column(nullable = false)
    private Double rechargeAmount;


    @Column(nullable = false)
    private Integer planValidity;


    private LocalDateTime rechargeDate;


    private LocalDateTime expiryDate;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RechargeStatus rechargeStatus;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RechargeType rechargeType;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


    public Recharge() {

    }


    public Recharge(Long rechargeId, User user, RechargePlan rechargePlan,
            Double rechargeAmount, Integer planValidity,
            LocalDateTime rechargeDate, LocalDateTime expiryDate,
            RechargeStatus rechargeStatus,
            RechargeType rechargeType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.rechargeId = rechargeId;
        this.user = user;
        this.rechargePlan = rechargePlan;
        this.rechargeAmount = rechargeAmount;
        this.planValidity = planValidity;
        this.rechargeDate = rechargeDate;
        this.expiryDate = expiryDate;
        this.rechargeStatus = rechargeStatus;
        this.rechargeType = rechargeType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }



    public Long getRechargeId() {
        return rechargeId;
    }


    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
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

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        rechargeDate = LocalDateTime.now();

        if(rechargeStatus == null) {
            rechargeStatus = RechargeStatus.PENDING;
        }

    }



    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();

    }

}