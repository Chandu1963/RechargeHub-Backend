package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ============================================================
    // UPDATE USER
    // USER + ADMIN
    // USER can update their profile
    // ADMIN can update user status from Admin Users page
    // ============================================================
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody User user) {

        User updatedUser =
                service.updateUser(userId, user);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User updated successfully",
                        updatedUser,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // GET USER BY ID
    // USER + ADMIN
    // ============================================================
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(
            @PathVariable Long userId) {

        User user =
                service.getUserById(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User fetched successfully",
                        user,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // DELETE USER
    // ADMIN ONLY
    // ============================================================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            @PathVariable Long userId) {

        String response =
                service.deleteUser(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        response,
                        null,
                        LocalDateTime.now()
                )
        );
    }

    // ============================================================
    // GET ALL USERS
    // ADMIN ONLY
    // ============================================================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

        List<User> users =
                service.getAllUsers();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Users fetched successfully",
                        users,
                        LocalDateTime.now()
                )
        );
    }
}