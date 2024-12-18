package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fluffyunicorns.R

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ui_checkout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backIcon: ImageButton = findViewById(R.id.backIcon)

        backIcon.setOnClickListener {
            val intent = Intent(this, ServiceActivity::class.java)
            startActivity(intent)
        }

        val SettingButton: ImageButton = findViewById(R.id.SettingButton)

        SettingButton.setOnClickListener {
            val intent = Intent(this, PaymentMethodsCheckoutActivity::class.java)
            startActivity(intent)
        }

        val btnNext: Button = findViewById(R.id.btnNext)

        btnNext.setOnClickListener {
            val intent = Intent(this, SuccessfulActivity::class.java)
            startActivity(intent)
        }
    }
}