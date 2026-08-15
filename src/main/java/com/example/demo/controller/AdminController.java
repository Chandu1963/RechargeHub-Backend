package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AdminRequest;
import com.example.demo.dto.AdminResponse;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*")
public class AdminController {


    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }



    @PostMapping("/save")
    public ResponseEntity<ApiResponse<AdminResponse>> registerAdmin(
            @Valid @RequestBody AdminRequest request) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin registered successfully",
                        adminService.registerAdmin(request),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{adminId}")
    public ResponseEntity<ApiResponse<AdminResponse>> updateAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminRequest request) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin updated successfully",
                        adminService.updateAdmin(adminId, request),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<ApiResponse<String>> deleteAdmin(
            @PathVariable Long adminId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        adminService.deleteAdmin(adminId),
                        null,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{adminId}")
    public ResponseEntity<ApiResponse<AdminResponse>> getAdminById(
            @PathVariable Long adminId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admin fetched successfully",
                        adminService.getAdminById(adminId),
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AdminResponse>>> getAllAdmins() {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Admins fetched successfully",
                        adminService.getAllAdmins(),
                        LocalDateTime.now()
                )
        );
    }

}