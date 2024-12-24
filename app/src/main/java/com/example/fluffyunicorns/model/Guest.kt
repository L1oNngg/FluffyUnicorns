package com.example.fluffyunicorns.model

data class GuestResponse(
    val success: Boolean,
    val data: GuestRequest
)

data class GuestRequest(
    val CustomerID: Int,
    val FirstName: String,
    val LastName: String,
    val DateOfBirth: String,
    val IDNumber: String
)