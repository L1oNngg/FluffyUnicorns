package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.adapter.BookingHistoryAdapter
import com.example.fluffyunicorns.api.BookingAPI
import com.example.fluffyunicorns.model.BookingHistory
import com.example.fluffyunicorns.model.BookingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryTabActivity : AppCompatActivity() {
    private lateinit var bookingAdapter: BookingHistoryAdapter
    private lateinit var recyclerView: RecyclerView

    // Global customer ID (you mentioned this should be global)
    companion object {
        var CUSTOMER_ID: Int = 1 // Set this when user logs in
    }

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
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.11.10.13/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookingApi = retrofit.create(BookingAPI::class.java)
        
        bookingApi.getBookingHistory(CUSTOMER_ID).enqueue(object : Callback<BookingResponse> {
            override fun onResponse(call: Call<BookingResponse>, response: Response<BookingResponse>) {
                if (response.isSuccessful) {
                    val bookings = response.body()?.data ?: emptyList()
                    bookingAdapter = BookingHistoryAdapter(bookings)
                    recyclerView.adapter = bookingAdapter
                }
            }

            override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                // Handle network error
                Toast.makeText(this@HistoryTabActivity, "Error fetching bookings", Toast.LENGTH_SHORT).show()
            }
        })
    }
}