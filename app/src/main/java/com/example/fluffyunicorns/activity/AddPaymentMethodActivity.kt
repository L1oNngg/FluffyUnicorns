package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R

class AddPaymentMethodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ui_add_payment_method)

        val SaveBtn: Button = findViewById(R.id.SaveBtn)

        SaveBtn.setOnClickListener {
            val intent = Intent(this, PaymentCardsActivity::class.java)
            startActivity(intent)
        }

        val imageView5: ImageView = findViewById(R.id.imageView5)

        imageView5.setOnClickListener {
            val intent = Intent(this, PaymentCardsActivity::class.java)
            startActivity(intent)
        }
    }
}