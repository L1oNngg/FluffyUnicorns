package com.example.fluffyunicorns.api
import com.example.fluffyunicorns.model.BookingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BookingAPI {
    @GET("booking/history/{customerID}")
    fun getBookingHistory(@Path("customerID") customerID: Int): Call<BookingResponse>
}
