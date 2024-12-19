package com.example.fluffyunicorns.model

import com.google.gson.*
import java.lang.reflect.Type

data class EditAccountRequest(
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

data class EditAccountResponse(
    val success: Boolean,
    val data: AccountData
)


