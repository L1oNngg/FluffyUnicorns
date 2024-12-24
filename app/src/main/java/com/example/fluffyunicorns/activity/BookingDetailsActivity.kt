package com.example.fluffyunicorns.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.api.RetrofitClient
import com.example.fluffyunicorns.model.BookingResponse
import com.example.fluffyunicorns.model.BookingHistory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingDetailsActivity : AppCompatActivity() {
    private lateinit var checkInDate: TextView
    private lateinit var checkOutDate: TextView
    private lateinit var occupacity: TextView
    private lateinit var roomTypeName: TextView
    private lateinit var totalNight: TextView
    private lateinit var pricePerNight: TextView
    private lateinit var service1: TextView
    private lateinit var service2: TextView
    private lateinit var service3: TextView
    private lateinit var service1Price: TextView
    private lateinit var service2Price: TextView
    private lateinit var service3Price: TextView
    private lateinit var totalPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_booking_details)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val customerID = sharedPreferences.getInt("customerID", -1)

        if (customerID == -1) {
            Toast.makeText(this, "Invalid customer ID", Toast.LENGTH_SHORT).show()
            return
        }

        val backIcon: ImageView = findViewById(R.id.backIcon)
        backIcon.setOnClickListener {
            finish() // Navigates back
        }

        initializeViews()
        fetchBookingDetails(customerID)
    }

    private fun initializeViews() {
        checkInDate = findViewById(R.id.check_in_date)
        checkOutDate = findViewById(R.id.check_out_date)
        occupacity = findViewById(R.id.Occupacity)
        roomTypeName = findViewById(R.id.RoomTypeName)
        totalNight = findViewById(R.id.total_night)
        pricePerNight = findViewById(R.id.price_per_night)
        totalPrice = findViewById(R.id.totalPrice)
        service1Price = findViewById(R.id.roomTypeName10)
        service2Price = findViewById(R.id.roomTypeName8)
        service3Price = findViewById(R.id.roomTypeName7)
        service1 = findViewById(R.id.LaundryTitle)
        service2 = findViewById(R.id.SpaTreatmentTitle)
        service3 = findViewById(R.id.service3Title)
    }

    private fun fetchBookingDetails(customerID: Int) {
        val bookingAPI = RetrofitClient.createBookingService()

        bookingAPI.getBookingHistory(customerID).enqueue(object : Callback<BookingResponse> {
            override fun onResponse(
                call: Call<BookingResponse>,
                response: Response<BookingResponse>
            ) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val bookingHistory = response.body()?.data?.firstOrNull()
                    if (bookingHistory != null) {
                        updateUI(bookingHistory)
                    } else {
                        Toast.makeText(this@BookingDetailsActivity, "No bookings found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@BookingDetailsActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                Toast.makeText(this@BookingDetailsActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Failure: ${t.message}")
            }
        })
    }

    private fun updateUI(bookingHistory: BookingHistory) {
        // Format CheckInDate and CheckOutDate
        val formattedCheckInDate = bookingHistory.CheckInDate.substringBefore("T")
        val formattedCheckOutDate = bookingHistory.CheckOutDate.substringBefore("T")

        // Update UI with formatted dates
        checkInDate.text = formattedCheckInDate
        checkOutDate.text = formattedCheckOutDate

        // Update other booking details
        occupacity.text = bookingHistory.total_guests
        roomTypeName.text = bookingHistory.TypeName
        totalNight.text = bookingHistory.total_nights.toString()
        pricePerNight.text = getString(R.string.price_format, bookingHistory.BasePrice)
        totalPrice.text = getString(R.string.total_price_format, bookingHistory.total_price.toString())

        // Update service details
        val services = bookingHistory.services

        if (services.isNotEmpty()) {
            service1.text = services[0].ServiceName ?: "N/A"
            service1Price.text = getString(R.string.price_format, services[0].UnitPrice?.toString() ?: "0")
        } else {
            service1.text = "No Service"
            service1Price.text = getString(R.string.price_format, "0")
        }

        if (services.size > 1) {
            service2.text = services[1].ServiceName ?: "N/A"
            service2Price.text = getString(R.string.price_format, services[1].UnitPrice?.toString() ?: "0")
        } else {
            service2.text = "No Service"
            service2Price.text = getString(R.string.price_format, "0")
        }

        if (services.size > 2) {
            service3.text = services[2].ServiceName ?: "N/A"
            service3Price.text = getString(R.string.price_format, services[2].UnitPrice?.toString() ?: "0")
        } else {
            service3.text = "No Service"
            service3Price.text = getString(R.string.price_format, "0")
        }
    }


}
