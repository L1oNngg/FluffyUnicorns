package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R

class SettingsTabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_settings_tab)

        val about: FrameLayout = findViewById(R.id.about)

        about.setOnClickListener {
            val intent = Intent(this, UserAccountActivity::class.java)
            startActivity(intent)
        }

        val pc: FrameLayout = findViewById(R.id.pc)

        pc.setOnClickListener {
            val intent = Intent(this, PaymentCardsActivity::class.java)
            startActivity(intent)
        }

        val lo: FrameLayout = findViewById(R.id.lo)

        lo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val HomeBtn: ImageView = findViewById(R.id.iconHome)

        HomeBtn.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}