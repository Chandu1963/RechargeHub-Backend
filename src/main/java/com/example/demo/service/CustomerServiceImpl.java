package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomerRequest;
import com.example.demo.entity.Customer;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer registerCustomer(CustomerRequest request) {

        if (repository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException("Customer with this mobile number already exists.");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Customer with this email already exists.");
        }

        Customer customer = new Customer();

        customer.setCustomerName(request.getCustomerName());
        customer.setMobileNumber(request.getMobileNumber());
        customer.setEmail(request.getEmail());
        customer.setCircle(request.getCircle());

        if (request.getStatus() != null) {
            customer.setStatus(request.getStatus());
        }

        return repository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long customerId, CustomerRequest request) {

        Customer existingCustomer = repository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with ID: " + customerId));

        // Check duplicate mobile number only if it is changed
        if (!existingCustomer.getMobileNumber().equals(request.getMobileNumber())) {

            repository.findByMobileNumber(request.getMobileNumber())
                    .ifPresent(customer -> {
                        throw new DuplicateResourceException(
                                "Customer with this mobile number already exists.");
                    });
        }

        // Check duplicate email only if it is changed
        if (!existingCustomer.getEmail().equals(request.getEmail())) {

            repository.findByEmail(request.getEmail())
                    .ifPresent(customer -> {
                        throw new DuplicateResourceException(
                                "Customer with this email already exists.");
                    });
        }

        existingCustomer.setCustomerName(request.getCustomerName());
        existingCustomer.setMobileNumber(request.getMobileNumber());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setCircle(request.getCircle());

        if (request.getStatus() != null) {
            existingCustomer.setStatus(request.getStatus());
        }

        return repository.save(existingCustomer);
    }

    @Override
    public String deleteCustomer(Long customerId) {

        Customer customer = repository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with ID: " + customerId));

        repository.delete(customer);

        return "Customer Deleted Successfully";
    }

    @Override
    public Customer getCustomerById(Long customerId) {

        return repository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with ID: " + customerId));
    }

    @Override
    public Customer getCustomerByMobileNumber(String mobileNumber) {

        return repository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with mobile number: " + mobileNumber));
    }

    @Override
    public List<Customer> getAllCustomers() {

        return repository.findAll();
    }
}