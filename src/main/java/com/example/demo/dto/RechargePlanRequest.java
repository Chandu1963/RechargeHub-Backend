package com.example.demo.dto;

import com.example.demo.enums.PlanCategory;
import com.example.demo.enums.PlanStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class RechargePlanRequest {

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotNull(message = "Category is required")
    private PlanCategory category;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Validity is required")
    @Positive(message = "Validity must be greater than zero")
    private Integer validityDays;

    private String dataBenefits;

    private String talktime;

    @PositiveOrZero(message = "SMS per day cannot be negative")
    private Integer smsPerDay;

    private String description;

    private PlanStatus status;

    public RechargePlanRequest() {

    }

    public RechargePlanRequest(String planName,
                               PlanCategory category,
                               Double price,
                               Integer validityDays,
                               String dataBenefits,
                               String talktime,
                               Integer smsPerDay,
                               String description,
                               PlanStatus status) {

        this.planName = planName;
        this.category = category;
        this.price = price;
        this.validityDays = validityDays;
        this.dataBenefits = dataBenefits;
        this.talktime = talktime;
        this.smsPerDay = smsPerDay;
        this.description = description;
        this.status = status;
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

}