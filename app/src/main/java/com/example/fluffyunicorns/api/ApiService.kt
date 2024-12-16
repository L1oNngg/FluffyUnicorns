package com.example.fluffyunicorns.api

//import com.example.fluffyunicorns.model.RegisterRequest
import com.example.fluffyunicorns.model.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("aaa")
    fun registerUser(): Call<ApiResponse>
}


