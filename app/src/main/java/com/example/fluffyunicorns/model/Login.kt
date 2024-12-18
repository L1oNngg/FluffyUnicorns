package com.example.fluffyunicorns.model

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val customerID: Int,
    val message: String
)