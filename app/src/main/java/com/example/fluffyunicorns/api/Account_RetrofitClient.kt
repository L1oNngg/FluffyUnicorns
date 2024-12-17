package com.example.fluffyunicorns.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Account_RetrofitClient {
    private const val BASE_URL = "http://10.11.10.13/api/"

    // Create and expose a Retrofit service interface instance
    val instance: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY) // Logs full response
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging) // Add logging interceptor
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

    // Create the service from the Retrofit instance
    fun createService(): AccountAPI = instance.create(AccountAPI::class.java)
}
