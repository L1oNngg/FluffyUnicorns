package com.example.fluffyunicorns.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.api.RetrofitClient
import com.example.fluffyunicorns.model.RoomDetails
import com.example.fluffyunicorns.model.RoomDetailsResponse
import com.example.fluffyunicorns.model.RoomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingActivity : AppCompatActivity() {
    private lateinit var mainImage: ImageView
    private lateinit var subImage1: ImageView
    private lateinit var subImage2: ImageView
    private lateinit var roomNameText: TextView
    private lateinit var roomTypeText: TextView
    private lateinit var roomPriceText: TextView
    private lateinit var roomPrice: TextView
    private lateinit var roomAreaText: TextView
    private lateinit var roomOccupancyText: TextView

    // Amenity ImageViews
    private lateinit var fridgeIcon: ImageView
    private lateinit var wifiIcon: ImageView
    private lateinit var bathtubIcon: ImageView
    private lateinit var hottubIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_booking)

        // Get RoomID from intent
        val roomId = intent.getIntExtra("ROOM_ID", 2) // Default to 2 if not provided

        // Initialize views
        mainImage = findViewById(R.id.mainImage)
        subImage1 = findViewById(R.id.subImage1)
        subImage2 = findViewById(R.id.subImage2)
        roomNameText = findViewById(R.id.roomName)
        roomTypeText = findViewById(R.id.typeRoom)
        roomPrice = findViewById(R.id.roomPrice)
        roomPriceText = findViewById(R.id.pricePerNight)
        roomAreaText = findViewById(R.id.Area)
        roomOccupancyText = findViewById(R.id.maxOccupancy)

        // Amenity Icons
        fridgeIcon = findViewById(R.id.ivFridge)
        wifiIcon = findViewById(R.id.ivWifi)
        bathtubIcon = findViewById(R.id.ivBathtub)
        hottubIcon = findViewById(R.id.ivHotTub)

        // Fetch room details (hardcoded RoomID 2 for this example)
        fetchRoomDetails(roomId)

        val bookBtn: Button = findViewById(R.id.bookBtn)
        bookBtn.setOnClickListener {
            val intent = Intent(this, CustomerInformationActivity::class.java)
            startActivity(intent)
        }

        val backIcon: ImageButton = findViewById(R.id.backIcon)
        backIcon.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchRoomDetails(roomId: Int) {
        val roomApi = RetrofitClient.createRoomService()
        roomApi.getRoomDetails(roomId).enqueue(object : Callback<RoomDetailsResponse> {
            override fun onResponse(call: Call<RoomDetailsResponse>, response: Response<RoomDetailsResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val room = response.body()
                    room?.let { displayRoomDetails(it) }
                } else {
                    Toast.makeText(this@BookingActivity, "Failed to fetch room details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RoomDetailsResponse>, t: Throwable) {
                Toast.makeText(this@BookingActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayRoomDetails(room: RoomDetailsResponse) {
        // Set text details
        roomNameText.text = "${room.data.TypeName} ${room.data.RoomNumber}"
        roomTypeText.text = room.data.TypeName
        roomPrice.text = "$ ${room.data.BasePrice}"
        roomPriceText.text = "$ ${room.data.BasePrice}"
        roomAreaText.text = "${room.data.Area} m²"
        roomOccupancyText.text = "${room.data.MaxOccupancy} Person"

        // Load images using Glide
        if (room.data.images.size >= 3) {
            Glide.with(this).load(room.data.images[2]).into(mainImage)
            Glide.with(this).load(room.data.images[0]).into(subImage1)
            Glide.with(this).load(room.data.images[1]).into(subImage2)
        }

        // Set amenity icons visibility
        fridgeIcon.visibility = if ("Fridge" in room.data.amenities) View.VISIBLE else View.GONE
        wifiIcon.visibility = if ("Wi-Fi" in room.data.amenities) View.VISIBLE else View.GONE
        bathtubIcon.visibility = if ("Bathtub" in room.data.amenities) View.VISIBLE else View.GONE
        hottubIcon.visibility = if ("Hot Tub" in room.data.amenities) View.VISIBLE else View.GONE
    }

    private fun setOnClickListenerForImage(imageView: ImageView, imageUrl: String) {
        imageView.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_image_fullscreen)

            val dialogImageView = dialog.findViewById<ImageView>(R.id.dialogImageView)
            Glide.with(this).load(imageUrl).into(dialogImageView)

            dialog.show()
        }
    }
}