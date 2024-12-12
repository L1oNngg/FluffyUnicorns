package com.example.fluffyunicorns.model

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val password: String
)

data class ApiResponse(
    val success: Boolean,
    val message: String
)