package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.api.AccountAPI
import com.example.fluffyunicorns.api.Account_RetrofitClient
import com.example.fluffyunicorns.model.AccountResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAccountActivity : AppCompatActivity() {
    // Declare UI elements
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var email: TextView
    private lateinit var idNumber: TextView
    private lateinit var phone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_account)

        // Initialize the views by linking them to the UI components
        firstName = findViewById(R.id.Firstname)
        lastName = findViewById(R.id.Lastname)
        email = findViewById(R.id.email)
        idNumber = findViewById(R.id.IDNumber)
        phone = findViewById(R.id.phone)

        // Retrieve customerID from SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val customerID = sharedPreferences.getInt("customerID", -1)

        if (customerID != -1) {
            // Fetch user account details if customerID is valid
            fetchAccountDetails(customerID)
        } else {
            Toast.makeText(this, "User information not found", Toast.LENGTH_SHORT).show()
        }

        val EditBtn: Button = findViewById(R.id.button)

        EditBtn.setOnClickListener {
            val intent = Intent(this, EditUserAccountActivity::class.java)
            startActivity(intent)
        }

        val BackBtn: Button = findViewById(R.id.btnBack)

        BackBtn.setOnClickListener {
            val intent = Intent(this, SettingsTabActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAccountDetails(customerID: Int) {
        // Get an instance of the API service
        val accountAPI = Account_RetrofitClient.instance.create(AccountAPI::class.java)
        val call = accountAPI.getAccountDetails(customerID)

        // Make the API call
        call.enqueue(object : Callback<AccountResponse> {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
                if (response.isSuccessful) {
                    val accountData = response.body()?.data
                    if (accountData != null) {
                        // Populate the UI with account details, handle potential nulls
                        firstName.text = accountData.FirstName ?: "N/A"
                        lastName.text = accountData.LastName ?: "N/A"
                        email.text = accountData.Email ?: "N/A"
                        idNumber.text = accountData.IDNumber?.trim() ?: "N/A" // Safely handle null for idNumber
                        phone.text = accountData.Phone ?: "N/A"
                    }
                } else {
                    Toast.makeText(this@UserAccountActivity, "Failed to load user information", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                Toast.makeText(this@UserAccountActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
