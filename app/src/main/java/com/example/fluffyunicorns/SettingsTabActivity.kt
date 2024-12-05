package com.example.fluffyunicorns

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsTabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.ui_settings_tab)

        val about: FrameLayout = findViewById(R.id.about)

        about.setOnClickListener {
            val intent = Intent(this, UserAccountActivity::class.java)
            startActivity(intent)
        }

        val editaccount: FrameLayout = findViewById(R.id.editaccount)

        editaccount.setOnClickListener {
            val intent = Intent(this, EditUserAccountActivity::class.java)
            startActivity(intent)
        }

        val pc: FrameLayout = findViewById(R.id.pc)

        pc.setOnClickListener {
            val intent = Intent(this, AddPaymentMethodActivity::class.java)
            startActivity(intent)
        }

        val lo: FrameLayout = findViewById(R.id.lo)

        lo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}