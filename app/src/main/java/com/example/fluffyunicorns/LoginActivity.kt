package com.example.fluffyunicorns

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ui_login)

        val tvRegister: TextView = findViewById(R.id.tvRegister)

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val LoginBtn: Button = findViewById(R.id.LoginBtn)

        LoginBtn.setOnClickListener {
            val intent = Intent(this, AddPaymentMethodActivity::class.java)
            startActivity(intent)
        }
    }
}