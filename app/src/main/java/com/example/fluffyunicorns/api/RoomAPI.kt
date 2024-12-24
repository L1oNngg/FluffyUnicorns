package com.example.fluffyunicorns.api

import com.example.fluffyunicorns.model.AccountResponse
import com.example.fluffyunicorns.model.RoomDetails
import com.example.fluffyunicorns.model.RoomDetailsResponse
import com.example.fluffyunicorns.model.RoomResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomAPI {
    @GET("room")
    fun getRoom(): Call<RoomResponse>

    @GET("room/{roomID}")
    fun getRoomDetails(@Path("roomID") roomID: Int): Call<RoomDetailsResponse>
}