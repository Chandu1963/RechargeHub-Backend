package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CustomerRequest;
import com.example.demo.entity.Customer;

public interface CustomerService {

    Customer registerCustomer(CustomerRequest request);

    Customer updateCustomer(Long customerId, CustomerRequest request);

    String deleteCustomer(Long customerId);

    Customer getCustomerById(Long customerId);
    
    Customer getCustomerByMobileNumber(String mobileNumber);

    List<Customer> getAllCustomers();

}
  