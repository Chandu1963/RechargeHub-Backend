package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Customer;
import com.example.demo.entity.User;

public interface UserService {

    User createUser(Customer customer);

    User updateUser(Long userId, User user);

    String deleteUser(Long userId);

    User getUserById(Long userId);

    List<User> getAllUsers();

    Optional<User> getUserByCustomer(Customer customer);

    boolean existsByCustomer(Customer customer);

}