package com.example.fluffyunicorns

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ui_login)

//        val loginButton: Button = findViewById(R.id.loginButton)
//
//        loginButton.setOnClickListener {
//            val intent = Intent(this, NewActivity::class.java)
//            startActivity(intent)
//        }
    }
}