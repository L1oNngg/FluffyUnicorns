package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R

class PaymentCardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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