package com.example.fluffyunicorns.api
import com.example.fluffyunicorns.model.BookingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BookingAPI {
    @GET("booking/history/{id}")
    fun getBookingHistory(@Path("id") customerID: Int): Call<BookingResponse>
}
