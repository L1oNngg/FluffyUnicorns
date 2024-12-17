package com.example.fluffyunicorns.model

data class AccountResponse(
    val status: String,
    val message: String,
    val data: AccountData
)

data class AccountData(
    val accountID: Int,
    val customerID: Int,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val username: String,
    val email: String,
    val phone: String,
    val address: String,
    val idNumber: String,
    val rewardPoints: Int
)