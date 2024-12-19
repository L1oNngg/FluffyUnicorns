package com.example.fluffyunicorns.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fluffyunicorns.R

class BookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_booking)

        val imageView17 = findViewById<ImageView>(R.id.imageView17)
        val imageView18 = findViewById<ImageView>(R.id.imageView18)
        val imageView19 = findViewById<ImageView>(R.id.imageView19)
        val imageView20 = findViewById<ImageView>(R.id.imageView20)

        // Thêm sự kiện click cho từng ImageView
        setOnClickListenerForImage(imageView17, R.drawable.room_05)
        setOnClickListenerForImage(imageView18, R.drawable.room_06)
        setOnClickListenerForImage(imageView19, R.drawable.room_07)
        setOnClickListenerForImage(imageView20, R.drawable.room_08)
    }

    private fun setOnClickListenerForImage(imageView: ImageView, imageResource: Int) {
        imageView.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_image_fullscreen)

            // Gán ảnh vào ImageView trong Dialog
            val dialogImageView = dialog.findViewById<ImageView>(R.id.dialogImageView)
            dialogImageView.setImageResource(imageResource)

            dialog.show()

            val bookBtn: Button = findViewById(R.id.bookBtn)

            bookBtn.setOnClickListener {
                val intent = Intent(this, CustomerInformationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}