
package com.example.demo.dto;

public class JwtResponse {

    private String token;
    private String role;
    private String message;
    private Long customerId;

    public JwtResponse() {
    }

    public JwtResponse(String token, String role, String message, Long customerId) {
        this.token = token;
        this.role = role;
        this.message = message;
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
