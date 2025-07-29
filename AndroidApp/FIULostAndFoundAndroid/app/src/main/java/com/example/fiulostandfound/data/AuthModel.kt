package com.example.fiulostandfound.data

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String, val role: String)


data class RegisterRequest(val username: String, val password: String)

data class AuthResponse(val token: String)