package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.enums.AdminStatus;

public class AdminResponse {

    private Long adminId;

    private String adminName;

    private String email;

    private String mobileNumber;

    private AdminStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public AdminResponse() {
    }


    public AdminResponse(Long adminId, String adminName,
                         String email, String mobileNumber,
                         AdminStatus status,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt) {

        this.adminId = adminId;
        this.adminName = adminName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }


    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    public AdminStatus getStatus() {
        return status;
    }

    public void setStatus(AdminStatus status) {
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

}