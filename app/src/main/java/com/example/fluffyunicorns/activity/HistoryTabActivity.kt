package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R

class HistoryTabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_history_tab)

        val iconHome: ImageView = findViewById(R.id.iconHome)

        iconHome.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val iconSettings: ImageView = findViewById(R.id.iconSettings)

        iconSettings.setOnClickListener {
            val intent = Intent(this, SettingsTabActivity::class.java)
            startActivity(intent)
        }
    }
}