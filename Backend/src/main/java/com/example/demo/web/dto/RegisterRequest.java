package com.example.demo.web.dto;

public class RegisterRequest {
  private String username;
  private String password;
  private String role;

  public String getUsername() {
    return username;
  }

  public void setUsername(String u) {
    this.username = u;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String p) {
    this.password = p;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}