package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.AdminRequest;
import com.example.demo.dto.AdminResponse;

public interface AdminService {

    AdminResponse registerAdmin(AdminRequest request);

    AdminResponse updateAdmin(Long adminId, AdminRequest request);

    String deleteAdmin(Long adminId);

    AdminResponse getAdminById(Long adminId);

    List<AdminResponse> getAllAdmins();

}