package com.example.demo.dto;

import com.example.demo.enums.RechargeType;

import jakarta.validation.constraints.NotNull;

public class RechargeRequest {


    @NotNull(message = "User ID is required")
    private Long userId;


    @NotNull(message = "Plan ID is required")
    private Long planId;


    @NotNull(message = "Recharge type is required")
    private RechargeType rechargeType;



    public RechargeRequest() {

    }


    public RechargeRequest(Long userId, Long planId,
            RechargeType rechargeType) {

        this.userId = userId;
        this.planId = planId;
        this.rechargeType = rechargeType;

    }



    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getPlanId() {
        return planId;
    }


    public void setPlanId(Long planId) {
        this.planId = planId;
    }


    public RechargeType getRechargeType() {
        return rechargeType;
    }


    public void setRechargeType(RechargeType rechargeType) {
        this.rechargeType = rechargeType;
    }

}