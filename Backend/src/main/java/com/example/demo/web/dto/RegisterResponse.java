package com.example.demo.web.dto;

public class RegisterResponse {
    private String username;
    public RegisterResponse(String username) {
        this.username = username;
    }
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
}