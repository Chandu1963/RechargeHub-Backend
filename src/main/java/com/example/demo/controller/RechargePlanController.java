package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.RechargePlanRequest;
import com.example.demo.dto.RechargePlanResponse;
import com.example.demo.service.RechargePlanService;


@RestController
@RequestMapping("/rechargeplans")
@CrossOrigin(origins = "*")
public class RechargePlanController {


    private final RechargePlanService service;


    public RechargePlanController(RechargePlanService service) {
        this.service = service;
    }



    // ==========================
    // ADMIN ONLY
    // ==========================


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<RechargePlanResponse>> saveRechargePlan(
            @RequestBody RechargePlanRequest request) {


        RechargePlanResponse response =
                service.saveRechargePlan(request);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge plan created successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }




    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{planId}")
    public ResponseEntity<ApiResponse<RechargePlanResponse>> updateRechargePlan(
            @PathVariable Long planId,
            @RequestBody RechargePlanRequest request) {


        RechargePlanResponse response =
                service.updateRechargePlan(planId, request);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge plan updated successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }




    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<ApiResponse<String>> deleteRechargePlan(
            @PathVariable Long planId) {


        String response =
                service.deleteRechargePlan(planId);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        LocalDateTime.now()
                )
        );
    }




    // ==========================
    // USER + ADMIN
    // ==========================


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{planId}")
    public ResponseEntity<ApiResponse<RechargePlanResponse>> getRechargePlanById(
            @PathVariable Long planId) {


        RechargePlanResponse response =
                service.getRechargePlanById(planId);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge plan fetched successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }




    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RechargePlanResponse>>> getAllRechargePlans() {


        List<RechargePlanResponse> response =
                service.getAllRechargePlans();


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Recharge plans fetched successfully",
                        response,
                        LocalDateTime.now()
                )
        );
    }

}