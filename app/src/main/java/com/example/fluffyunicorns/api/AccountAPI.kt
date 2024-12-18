package com.example.fluffyunicorns.api

import com.example.fluffyunicorns.model.AccountResponse
import com.example.fluffyunicorns.model.RegisterResponse
import com.example.fluffyunicorns.model.LoginRequest
import com.example.fluffyunicorns.model.LoginResponse
import com.example.fluffyunicorns.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountAPI {
    @POST("register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("account/{id}")
    fun getAccountDetails(@Path("id") id: Int): Call<AccountResponse>
}
