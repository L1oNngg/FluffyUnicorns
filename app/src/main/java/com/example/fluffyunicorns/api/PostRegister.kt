package com.example.fluffyunicorns.api

import com.example.fluffyunicorns.model.RegisterRequest
import com.example.fluffyunicorns.model.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface `/register` {
    @POST("/register")
    fun registerUser(@Body request: RegisterRequest): Call<ApiResponse>
}


