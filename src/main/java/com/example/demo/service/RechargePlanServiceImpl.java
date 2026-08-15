package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RechargePlanRequest;
import com.example.demo.dto.RechargePlanResponse;
import com.example.demo.entity.RechargePlan;
import com.example.demo.enums.PlanStatus;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RechargePlanRepository;

@Service
public class RechargePlanServiceImpl implements RechargePlanService {

    private static final Logger log =
            LoggerFactory.getLogger(RechargePlanServiceImpl.class);

    @Autowired
    private RechargePlanRepository repository;

    @Override
    public RechargePlanResponse saveRechargePlan(RechargePlanRequest request) {

        log.info("Creating Recharge Plan : {}", request.getPlanName());

        if (repository.existsByPlanName(request.getPlanName())) {
            throw new DuplicateResourceException(
                    "Recharge Plan already exists with name : "
                            + request.getPlanName());
        }

        RechargePlan plan = new RechargePlan();

        plan.setPlanName(request.getPlanName());
        plan.setCategory(request.getCategory());
        plan.setPrice(request.getPrice());
        plan.setValidityDays(request.getValidityDays());
        plan.setDataBenefits(request.getDataBenefits());
        plan.setTalktime(request.getTalktime());
        plan.setSmsPerDay(request.getSmsPerDay());
        plan.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            plan.setStatus(request.getStatus());
        } else {
            plan.setStatus(PlanStatus.ACTIVE);
        }

        RechargePlan savedPlan = repository.save(plan);

        log.info("Recharge Plan Created Successfully : {}",
                savedPlan.getPlanId());

        return mapToResponse(savedPlan);
    }

    @Override
    public RechargePlanResponse updateRechargePlan(
            Long planId,
            RechargePlanRequest request) {

        log.info("Updating Recharge Plan : {}", planId);

        RechargePlan existingPlan = repository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recharge Plan not found with ID : "
                                        + planId));

        if (!existingPlan.getPlanName().equalsIgnoreCase(request.getPlanName())
                && repository.existsByPlanName(request.getPlanName())) {

            throw new DuplicateResourceException(
                    "Recharge Plan already exists with name : "
                            + request.getPlanName());
        }

        existingPlan.setPlanName(request.getPlanName());
        existingPlan.setCategory(request.getCategory());
        existingPlan.setPrice(request.getPrice());
        existingPlan.setValidityDays(request.getValidityDays());
        existingPlan.setDataBenefits(request.getDataBenefits());
        existingPlan.setTalktime(request.getTalktime());
        existingPlan.setSmsPerDay(request.getSmsPerDay());
        existingPlan.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            existingPlan.setStatus(request.getStatus());
        }

        RechargePlan updatedPlan = repository.save(existingPlan);

        log.info("Recharge Plan Updated Successfully : {}", planId);

        return mapToResponse(updatedPlan);
    }

    @Override
    public String deleteRechargePlan(Long planId) {

        log.info("Deleting Recharge Plan : {}", planId);

        RechargePlan plan = repository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recharge Plan not found with ID : "
                                        + planId));

        repository.delete(plan);

        log.info("Recharge Plan Deleted Successfully : {}", planId);

        return "Recharge Plan Deleted Successfully";
    }

    @Override
    public RechargePlanResponse getRechargePlanById(Long planId) {

        log.info("Fetching Recharge Plan : {}", planId);

        RechargePlan plan = repository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recharge Plan not found with ID : "
                                        + planId));

        return mapToResponse(plan);
    }

    @Override
    public List<RechargePlanResponse> getAllRechargePlans() {

        log.info("Fetching All Recharge Plans");

        List<RechargePlan> plans = repository.findAll();

        List<RechargePlanResponse> responseList = new ArrayList<>();

        for (RechargePlan plan : plans) {
            responseList.add(mapToResponse(plan));
        }

        log.info("Total Recharge Plans Found : {}", responseList.size());

        return responseList;
    }

    private RechargePlanResponse mapToResponse(RechargePlan plan) {

        RechargePlanResponse response = new RechargePlanResponse();

        response.setPlanId(plan.getPlanId());
        response.setPlanName(plan.getPlanName());
        response.setCategory(plan.getCategory());
        response.setPrice(plan.getPrice());
        response.setValidityDays(plan.getValidityDays());
        response.setDataBenefits(plan.getDataBenefits());
        response.setTalktime(plan.getTalktime());
        response.setSmsPerDay(plan.getSmsPerDay());
        response.setDescription(plan.getDescription());
        response.setStatus(plan.getStatus());
        response.setCreatedAt(plan.getCreatedAt());
        response.setUpdatedAt(plan.getUpdatedAt());

        return response;
    }
}