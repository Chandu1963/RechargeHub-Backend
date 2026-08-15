package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RechargeHistoryResponse;
import com.example.demo.entity.RechargeHistory;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.enums.RechargeStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RechargeHistoryRepository;

@Service
public class RechargeHistoryServiceImpl
        implements RechargeHistoryService {

    @Autowired
    private RechargeHistoryRepository rechargeHistoryRepository;

    @Override
    public RechargeHistory saveRechargeHistory(
            RechargeHistory rechargeHistory) {

        return rechargeHistoryRepository.save(rechargeHistory);
    }

    @Override
    public RechargeHistoryResponse getHistoryById(
            Long historyId) {

        RechargeHistory history =
                rechargeHistoryRepository.findById(historyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recharge History not found with ID : "
                                        + historyId));

        return mapToResponse(history);
    }

    @Override
    public List<RechargeHistoryResponse> getAllHistory() {

        List<RechargeHistory> histories =
                rechargeHistoryRepository.findAll();

        List<RechargeHistoryResponse> responseList =
                new ArrayList<>();

        for (RechargeHistory history : histories) {
            responseList.add(
                    mapToResponse(history));
        }

        return responseList;
    }
    @Override
    public List<RechargeHistoryResponse> getHistoryByCustomer(
            Long customerId) {

        List<RechargeHistory> histories =
                rechargeHistoryRepository
                        .findByCustomerCustomerId(customerId);

        List<RechargeHistoryResponse> responseList =
                new ArrayList<>();

        for (RechargeHistory history : histories) {
            responseList.add(
                    mapToResponse(history));
        }

        return responseList;
    }

    @Override
    public List<RechargeHistoryResponse> getHistoryByRecharge(
            Long rechargeId) {

        List<RechargeHistory> histories =
                rechargeHistoryRepository
                        .findByRechargeRechargeId(rechargeId);

        List<RechargeHistoryResponse> responseList =
                new ArrayList<>();

        for (RechargeHistory history : histories) {
            responseList.add(
                    mapToResponse(history));
        }

        return responseList;
    }

    @Override
    public List<RechargeHistoryResponse> getHistoryByPayment(
            Long paymentId) {

        List<RechargeHistory> histories =
                rechargeHistoryRepository
                        .findByPaymentPaymentId(paymentId);

        List<RechargeHistoryResponse> responseList =
                new ArrayList<>();

        for (RechargeHistory history : histories) {
            responseList.add(
                    mapToResponse(history));
        }

        return responseList;
    }

    @Override
    public List<RechargeHistoryResponse> getHistoryByPlan(
            Long planId) {

        List<RechargeHistory> histories =
                rechargeHistoryRepository
                        .findByRechargePlanPlanId(planId);

        List<RechargeHistoryResponse> responseList =
                new ArrayList<>();

        for (RechargeHistory history : histories) {
            responseList.add(
                    mapToResponse(history));
        }

        return responseList;
    }
    
    @Override
    public List<RechargeHistoryResponse> getHistoryByRechargeStatus(
            RechargeStatus rechargeStatus) {

        List<RechargeHistory> histories =
                rechargeHistoryRepository
                        .findByRechargeStatus(rechargeStatus);

        List<RechargeHistoryResponse> responseList =
                new ArrayList<>();

        for (RechargeHistory history : histories) {
            responseList.add(
                    mapToResponse(history));
        }

        return responseList;
    }

    @Override
    public List<RechargeHistoryResponse> getHistoryByPaymentStatus(
            PaymentStatus paymentStatus) {

        List<RechargeHistory> histories =
                rechargeHistoryRepository
                        .findByPaymentStatus(paymentStatus);

        List<RechargeHistoryResponse> responseList =
                new ArrayList<>();

        for (RechargeHistory history : histories) {
            responseList.add(
                    mapToResponse(history));
        }

        return responseList;
    }

    @Override
    public List<RechargeHistoryResponse> getHistoryBetweenDates(
            LocalDateTime startDate,
            LocalDateTime endDate) {

        List<RechargeHistory> histories =
                rechargeHistoryRepository
                        .findByRechargeDateBetween(startDate, endDate);

        List<RechargeHistoryResponse> responseList =
                new ArrayList<>();

        for (RechargeHistory history : histories) {
            responseList.add(
                    mapToResponse(history));
        }

        return responseList;
    }

    @Override
    public String deleteHistory(Long historyId) {

        RechargeHistory history =
                rechargeHistoryRepository.findById(historyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recharge History not found with ID : "
                                        + historyId));

        rechargeHistoryRepository.delete(history);

        return "Recharge History Deleted Successfully";
    }

    private RechargeHistoryResponse mapToResponse(
            RechargeHistory history) {

        RechargeHistoryResponse response =
                new RechargeHistoryResponse();

        response.setHistoryId(history.getHistoryId());

        response.setCustomerId(
                history.getCustomer().getCustomerId());

        response.setCustomerName(
                history.getCustomer().getCustomerName());

        response.setMobileNumber(
                history.getCustomer().getMobileNumber());

        response.setRechargeId(
                history.getRecharge().getRechargeId());

        response.setPaymentId(
                history.getPayment().getPaymentId());

        response.setPlanId(
                history.getRechargePlan().getPlanId());

        response.setPlanName(
                history.getRechargePlan().getPlanName());

        response.setRechargeAmount(
                history.getRechargeAmount());

        response.setRechargeStatus(
                history.getRechargeStatus());

        response.setPaymentStatus(
                history.getPaymentStatus());

        response.setRechargeDate(
                history.getRechargeDate());

        response.setCreatedAt(
                history.getCreatedAt());

        response.setUpdatedAt(
                history.getUpdatedAt());

        return response;
    }

}
