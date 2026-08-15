package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RechargePlanRequest;
import com.example.demo.dto.RechargePlanResponse;

public interface RechargePlanService {

    RechargePlanResponse saveRechargePlan(RechargePlanRequest request);

    RechargePlanResponse updateRechargePlan(Long planId, RechargePlanRequest request);

    String deleteRechargePlan(Long planId);

    RechargePlanResponse getRechargePlanById(Long planId);

    List<RechargePlanResponse> getAllRechargePlans();

}