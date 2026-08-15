package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.enums.PlanCategory;
import com.example.demo.enums.PlanStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "recharge_plans")
public class RechargePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @NotBlank
    @Column(name = "plan_name", nullable = false, length = 100)
    private String planName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanCategory category;


    @Column(nullable = false)
    private Double price;


    @Column(nullable = false)
    private Integer validityDays;


    private String dataBenefits;


    private String talktime;


    private Integer smsPerDay;


    private String description;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanStatus status;


    @Column(updatable = false)
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;



    public RechargePlan() {

    }


    public RechargePlan(Long planId,
                        String planName,
                        PlanCategory category,
                        Double price,
                        Integer validityDays,
                        String dataBenefits,
                        String talktime,
                        Integer smsPerDay,
                        String description,
                        PlanStatus status,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {

        this.planId = planId;
        this.planName = planName;
        this.category = category;
        this.price = price;
        this.validityDays = validityDays;
        this.dataBenefits = dataBenefits;
        this.talktime = talktime;
        this.smsPerDay = smsPerDay;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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


    public PlanCategory getCategory() {
        return category;
    }


    public void setCategory(PlanCategory category) {
        this.category = category;
    }


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }


    public Integer getValidityDays() {
        return validityDays;
    }


    public void setValidityDays(Integer validityDays) {
        this.validityDays = validityDays;
    }


    public String getDataBenefits() {
        return dataBenefits;
    }


    public void setDataBenefits(String dataBenefits) {
        this.dataBenefits = dataBenefits;
    }


    public String getTalktime() {
        return talktime;
    }


    public void setTalktime(String talktime) {
        this.talktime = talktime;
    }


    public Integer getSmsPerDay() {
        return smsPerDay;
    }


    public void setSmsPerDay(Integer smsPerDay) {
        this.smsPerDay = smsPerDay;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public PlanStatus getStatus() {
        return status;
    }


    public void setStatus(PlanStatus status) {
        this.status = status;
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

        if(status == null) {
            status = PlanStatus.ACTIVE;
        }
    }


    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();
    }

}