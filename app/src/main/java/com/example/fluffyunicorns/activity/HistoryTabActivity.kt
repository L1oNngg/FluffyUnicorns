package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.adapter.BookingHistoryAdapter
import com.example.fluffyunicorns.api.Booking_RetrofitClient
import com.example.fluffyunicorns.model.BookingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryTabActivity : AppCompatActivity() {
    private lateinit var bookingAdapter: BookingHistoryAdapter
    private lateinit var recyclerView: RecyclerView

    // Global customer ID (you mentioned this should be global)
    val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
    val customerID = sharedPreferences.getInt("customerID", -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_history_tab)

        val iconHome: ImageView = findViewById(R.id.iconHome)

        iconHome.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val iconSettings: ImageView = findViewById(R.id.iconSettings)

        iconSettings.setOnClickListener {
            val intent = Intent(this, SettingsTabActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.rvHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchBookingHistory()
    }

    private fun fetchBookingHistory() {
        val bookingApi = Booking_RetrofitClient.createService()
        bookingApi.getBookingHistory(customerID).enqueue(object : Callback<BookingResponse> {
            override fun onResponse(call: Call<BookingResponse>, response: Response<BookingResponse>) {
                if (response.isSuccessful) {
                    val bookings = response.body()?.data ?: emptyList()
                    bookingAdapter = BookingHistoryAdapter(this@HistoryTabActivity, bookings)
                    recyclerView.adapter = bookingAdapter
                } else {
                    Toast.makeText(this@HistoryTabActivity, "Failed to load bookings", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                Toast.makeText(this@HistoryTabActivity, "Error fetching bookings", Toast.LENGTH_SHORT).show()
            }
        })
    }
}