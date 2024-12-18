package com.example.fluffyunicorns.model

data class AccountResponse(
    val success: Boolean,
    val data: AccountData
)

data class AccountData(
    val AccountID: Int,
    val CustomerID: Int,
    val FirstName: String,
    val MiddleName: String?,
    val LastName: String,
    val Username: String,
    val Status: String,
    val DateOfBirth: String,
    val Gender: String,
    val Email: String,
    val Phone: String,
    val Address: String,
    val IDNumber: String,
    val RewardPoints: Int
)
