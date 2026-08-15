package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AdminRequest;
import com.example.demo.dto.AdminResponse;
import com.example.demo.entity.Admin;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {


    private final AdminRepository adminRepository;


    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public AdminResponse registerAdmin(AdminRequest request) {


        // Only one admin is allowed in RechargeHub system
        if (adminRepository.count() > 0) {

            throw new DuplicateResourceException(
                    "Admin already exists");
        }


        if (adminRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists");
        }


        if (adminRepository.existsByMobileNumber(request.getMobileNumber())) {

            throw new DuplicateResourceException(
                    "Mobile number already exists");
        }


        Admin admin = new Admin();

        admin.setAdminName(request.getAdminName());
        admin.setEmail(request.getEmail());
        admin.setMobileNumber(request.getMobileNumber());


        if (request.getStatus() != null) {

            admin.setStatus(request.getStatus());
        }


        Admin savedAdmin = adminRepository.save(admin);


        return mapToResponse(savedAdmin);
    }



    @Override
    public AdminResponse updateAdmin(Long adminId, AdminRequest request) {


        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin not found with ID : " + adminId));


        if (!admin.getEmail().equals(request.getEmail())) {

            adminRepository.findByEmail(request.getEmail())
                    .ifPresent(existing -> {

                        throw new DuplicateResourceException(
                                "Email already exists");
                    });
        }


        if (!admin.getMobileNumber()
                .equals(request.getMobileNumber())) {


            adminRepository.findByMobileNumber(request.getMobileNumber())
                    .ifPresent(existing -> {

                        throw new DuplicateResourceException(
                                "Mobile number already exists");
                    });
        }


        admin.setAdminName(request.getAdminName());
        admin.setEmail(request.getEmail());
        admin.setMobileNumber(request.getMobileNumber());


        if (request.getStatus() != null) {

            admin.setStatus(request.getStatus());
        }


        Admin updatedAdmin = adminRepository.save(admin);


        return mapToResponse(updatedAdmin);
    }



    @Override
    public String deleteAdmin(Long adminId) {


        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin not found with ID : " + adminId));


        adminRepository.delete(admin);


        return "Admin deleted successfully";
    }



    @Override
    public AdminResponse getAdminById(Long adminId) {


        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin not found with ID : " + adminId));


        return mapToResponse(admin);
    }



    @Override
    public List<AdminResponse> getAllAdmins() {


        return adminRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }



    private AdminResponse mapToResponse(Admin admin) {


        AdminResponse response = new AdminResponse();


        response.setAdminId(admin.getAdminId());
        response.setAdminName(admin.getAdminName());
        response.setEmail(admin.getEmail());
        response.setMobileNumber(admin.getMobileNumber());
        response.setStatus(admin.getStatus());
        response.setCreatedAt(admin.getCreatedAt());
        response.setUpdatedAt(admin.getUpdatedAt());


        return response;
    }

}