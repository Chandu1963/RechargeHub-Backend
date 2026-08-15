package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RechargePlan;
import com.example.demo.enums.PlanStatus;

@Repository
public interface RechargePlanRepository extends JpaRepository<RechargePlan, Long> {

    boolean existsByPlanName(String planName);

    List<RechargePlan> findByStatus(PlanStatus status);

}