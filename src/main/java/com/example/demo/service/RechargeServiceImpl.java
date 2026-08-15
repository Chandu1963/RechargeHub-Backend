package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ExpiringRechargeResponse;
import com.example.demo.dto.RechargeRequest;
import com.example.demo.dto.RechargeResponse;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Recharge;
import com.example.demo.entity.RechargePlan;
import com.example.demo.entity.User;
import com.example.demo.enums.CustomerStatus;
import com.example.demo.enums.PlanStatus;
import com.example.demo.enums.RechargeStatus;
import com.example.demo.enums.UserStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.RechargePlanRepository;
import com.example.demo.repository.RechargeRepository;
import com.example.demo.repository.UserRepository;

@Service
public class RechargeServiceImpl implements RechargeService {

    private static final Logger logger =
            LoggerFactory.getLogger(RechargeServiceImpl.class);

    @Autowired
    private RechargeRepository rechargeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RechargePlanRepository rechargePlanRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public RechargeResponse createRecharge(RechargeRequest request) {

        logger.info("Creating recharge for User ID : {}", request.getUserId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID : " + request.getUserId()));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("User account is not active.");
        }

        Customer customer = user.getCustomer();

        if (customer.getStatus() != CustomerStatus.ACTIVE) {
            throw new RuntimeException("Customer account is not active.");
        }

        RechargePlan rechargePlan = rechargePlanRepository
                .findById(request.getPlanId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recharge Plan not found with ID : " + request.getPlanId()));

        if (rechargePlan.getStatus() != PlanStatus.ACTIVE) {
            throw new RuntimeException("Recharge Plan is inactive.");
        }

        Recharge recharge = new Recharge();

        recharge.setUser(user);
        recharge.setRechargePlan(rechargePlan);
        recharge.setRechargeAmount(rechargePlan.getPrice());
        recharge.setPlanValidity(rechargePlan.getValidityDays());
        recharge.setRechargeType(request.getRechargeType());
        recharge.setRechargeStatus(RechargeStatus.PENDING);

        LocalDateTime rechargeDate = LocalDateTime.now();

        recharge.setRechargeDate(rechargeDate);
        recharge.setExpiryDate(
                rechargeDate.plusDays(rechargePlan.getValidityDays()));

        Recharge savedRecharge = rechargeRepository.save(recharge);

        logger.info("Recharge Created Successfully. Recharge ID : {}",
                savedRecharge.getRechargeId());

        return mapToResponse(savedRecharge);
    }
    @Override
    public RechargeResponse getRechargeById(Long rechargeId) {

        logger.info("Fetching Recharge with ID : {}", rechargeId);

        Recharge recharge = rechargeRepository.findById(rechargeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recharge not found with ID : " + rechargeId));

        return mapToResponse(recharge);
    }

    @Override
    public List<RechargeResponse> getAllRecharges() {

        logger.info("Fetching all recharges");

        List<Recharge> recharges = rechargeRepository.findAll();

        List<RechargeResponse> responseList = new ArrayList<>();

        for (Recharge recharge : recharges) {
            responseList.add(mapToResponse(recharge));
        }

        logger.info("Total Recharges Found : {}", responseList.size());

        return responseList;
    }

    @Override
    public List<RechargeResponse> getRechargesByUser(Long userId) {

        logger.info("Fetching recharges for User ID : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID : " + userId));

        List<Recharge> recharges =
                rechargeRepository.findByUserUserId(user.getUserId());

        List<RechargeResponse> responseList = new ArrayList<>();

        for (Recharge recharge : recharges) {
            responseList.add(mapToResponse(recharge));
        }

        logger.info("Total Recharges Found for User {} : {}",
                userId, responseList.size());

        return responseList;
    }

    @Override
    public String deleteRecharge(Long rechargeId) {

        logger.info("Deleting Recharge ID : {}", rechargeId);

        Recharge recharge = rechargeRepository.findById(rechargeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recharge not found with ID : " + rechargeId));

        if (paymentRepository.existsByRechargeRechargeId(rechargeId)) {
            throw new RuntimeException(
                    "Recharge cannot be deleted because payment has already been completed.");
        }

        rechargeRepository.delete(recharge);

        logger.info("Recharge Deleted Successfully : {}", rechargeId);

        return "Recharge Deleted Successfully";
    }
    @Override
    public List<ExpiringRechargeResponse> getExpiringRecharges() {

        logger.info("Fetching recharges expiring within the next 3 days");

        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime nextThreeDays = today.plusDays(3);

        List<Recharge> recharges = rechargeRepository
                .findByExpiryDateBetween(today, nextThreeDays);

        List<ExpiringRechargeResponse> responseList = new ArrayList<>();

        for (Recharge recharge : recharges) {

            Customer customer = recharge.getUser().getCustomer();

            long daysRemaining = ChronoUnit.DAYS.between(
                    today,
                    recharge.getExpiryDate().truncatedTo(ChronoUnit.DAYS));

            ExpiringRechargeResponse response =
                    new ExpiringRechargeResponse();

            response.setRechargeId(recharge.getRechargeId());
            response.setCustomerName(customer.getCustomerName());
            response.setMobileNumber(customer.getMobileNumber());
            response.setPlanName(recharge.getRechargePlan().getPlanName());
            response.setRechargeAmount(recharge.getRechargeAmount());
            response.setExpiryDate(recharge.getExpiryDate());
            response.setDaysRemaining(daysRemaining);

            responseList.add(response);
        }

        logger.info("Found {} expiring recharge(s)", responseList.size());

        return responseList;
    }

    private RechargeResponse mapToResponse(Recharge recharge) {

        Customer customer = recharge.getUser().getCustomer();

        RechargeResponse response = new RechargeResponse();

        response.setRechargeId(recharge.getRechargeId());
        response.setUserId(recharge.getUser().getUserId());
        response.setCustomerName(customer.getCustomerName());
        response.setMobileNumber(customer.getMobileNumber());
        response.setPlanId(recharge.getRechargePlan().getPlanId());
        response.setPlanName(recharge.getRechargePlan().getPlanName());
        response.setRechargeAmount(recharge.getRechargeAmount());
        response.setPlanValidity(recharge.getPlanValidity());
        response.setRechargeDate(recharge.getRechargeDate());
        response.setExpiryDate(recharge.getExpiryDate());
        response.setRechargeStatus(recharge.getRechargeStatus());
        response.setRechargeType(recharge.getRechargeType());

        return response;
    }

}
