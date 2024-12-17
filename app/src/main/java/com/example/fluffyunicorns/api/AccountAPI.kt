package com.example.fluffyunicorns.api

import com.example.fluffyunicorns.model.RegisterResponse
import com.example.fluffyunicorns.model.LoginRequest
import com.example.fluffyunicorns.model.LoginResponse
import com.example.fluffyunicorns.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountAPI {
    @POST("register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}
