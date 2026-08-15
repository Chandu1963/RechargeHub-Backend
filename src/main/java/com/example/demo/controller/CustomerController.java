package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CustomerRequest;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class CustomerController {


    private final CustomerService service;


    public CustomerController(CustomerService service) {
        this.service = service;
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Customer>> registerCustomer(
            @Valid @RequestBody CustomerRequest request) {


        Customer customer =
                service.registerCustomer(request);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Customer registered successfully",
                        customer,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{customerId}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request) {


        Customer customer =
                service.updateCustomer(customerId, request);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Customer updated successfully",
                        customer,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(
            @PathVariable Long customerId) {


        String response =
                service.deleteCustomer(customerId);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(
            @PathVariable Long customerId) {


        Customer customer =
                service.getCustomerById(customerId);


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Customer fetched successfully",
                        customer,
                        LocalDateTime.now()
                )
        );
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers() {


        List<Customer> customers =
                service.getAllCustomers();


        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Customers fetched successfully",
                        customers,
                        LocalDateTime.now()
                )
        );
    }

}