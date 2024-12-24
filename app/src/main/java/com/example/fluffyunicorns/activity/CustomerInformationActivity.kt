package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.api.RetrofitClient
import com.example.fluffyunicorns.model.GuestRequest
import com.example.fluffyunicorns.model.GuestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_customer_information)

        val backIcon: ImageButton = findViewById(R.id.backIcon)
        val btnNext: Button = findViewById(R.id.btnNext)

        val etLastName: EditText = findViewById(R.id.username)
        val etFirstName: EditText = findViewById(R.id.lastName)
        val etIDCard: EditText = findViewById(R.id.IDCard)
        val etGender: EditText = findViewById(R.id.gender)
        val etDateOfBirth: EditText = findViewById(R.id.dateOfBirth)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val customerID = sharedPreferences.getInt("customerID", -1)

        backIcon.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val lastName = etLastName.text.toString().trim()
            val firstName = etFirstName.text.toString().trim()
            val idCard = etIDCard.text.toString().trim()
            val gender = etGender.text.toString().trim()
            val dateOfBirth = etDateOfBirth.text.toString().trim()

            if (lastName.isEmpty() || firstName.isEmpty() || idCard.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Create request object
            val guestRequest = GuestRequest(
                CustomerID = customerID,
                FirstName = firstName,
                LastName = lastName,
                DateOfBirth = dateOfBirth,
                IDNumber = idCard
            )

            // Make API call
            val apiService = RetrofitClient.createGuestService()
            apiService.addGuest(guestRequest).enqueue(object : Callback<GuestResponse> {
                override fun onResponse(call: Call<GuestResponse>, response: Response<GuestResponse>) {
                    if (response.isSuccessful) {
                        val guestResponse = response.body()
                        Toast.makeText(this@CustomerInformationActivity, "Guest added successfully!", Toast.LENGTH_SHORT).show()

                        // Navigate to next activity
                        val intent = Intent(this@CustomerInformationActivity, ServiceActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@CustomerInformationActivity, "Failed to add guest", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GuestResponse>, t: Throwable) {
                    Toast.makeText(this@CustomerInformationActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
