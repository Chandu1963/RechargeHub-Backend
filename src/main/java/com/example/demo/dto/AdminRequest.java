package com.example.demo.dto;

import com.example.demo.enums.AdminStatus;

public class AdminRequest {

    private String adminName;

    private String email;

    private String mobileNumber;

    private AdminStatus status;


    public AdminRequest() {
    }


    public AdminRequest(String adminName, String email,
                        String mobileNumber, AdminStatus status) {

        this.adminName = adminName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.status = status;
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

}