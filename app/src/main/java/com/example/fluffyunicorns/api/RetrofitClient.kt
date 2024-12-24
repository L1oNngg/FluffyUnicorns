package com.example.fluffyunicorns.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.11.10.13/api/"

    // Shared Retrofit instance for all API services
    val instance: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    // Service creation methods for different APIs
    fun createGuestService(): GuestAPI = instance.create(GuestAPI::class.java)
    fun createAccountService(): AccountAPI = instance.create(AccountAPI::class.java)
    fun createBookingService(): BookingAPI = instance.create(BookingAPI::class.java)
    fun createRoomService(): RoomAPI = instance.create(RoomAPI::class.java)
}