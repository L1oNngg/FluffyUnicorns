package com.example.fluffyunicorns.api

import com.example.fluffyunicorns.model.GuestRequest
import com.example.fluffyunicorns.model.GuestResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GuestAPI {
    @POST("guests")
    fun addGuest(@Body loginRequest: GuestRequest): Call<GuestResponse>
}