package com.example.fluffyunicorns.model

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,  // Token received upon successful login
    val userId: Int,    // User ID
    val message: String // Login success/failure message
)