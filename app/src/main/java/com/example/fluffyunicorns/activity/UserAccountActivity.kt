package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R

class UserAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_account)

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val intent = Intent(this, EditUserAccountActivity::class.java)
            startActivity(intent)
        }

        val backIcon: ImageView = findViewById(R.id.backIcon)

        backIcon.setOnClickListener {
            val intent = Intent(this, SettingsTabActivity::class.java)
            startActivity(intent)
        }

        val btnBack: Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, SettingsTabActivity::class.java)
            startActivity(intent)
        }
    }
}
