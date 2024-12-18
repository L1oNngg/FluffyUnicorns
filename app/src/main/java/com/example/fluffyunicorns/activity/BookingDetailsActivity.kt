package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R

class BookingDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_booking_details)

        val backIcon: ImageView = findViewById(R.id.backIcon)

        backIcon.setOnClickListener {
            val intent = Intent(this, HistoryTabActivity::class.java)
            startActivity(intent)
        }
    }
}