package com.example.fluffyunicorns

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaymentCardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ui_payment_cards)

        val imageView7: ImageView = findViewById(R.id.imageView7)

        imageView7.setOnClickListener {
            val intent = Intent(this, SettingsTabActivity::class.java)
            startActivity(intent)
        }

        val frameAdd: FrameLayout = findViewById(R.id.frameAdd)

        frameAdd.setOnClickListener {
            val intent = Intent(this, AddPaymentMethodActivity::class.java)
            startActivity(intent)
        }
    }
}