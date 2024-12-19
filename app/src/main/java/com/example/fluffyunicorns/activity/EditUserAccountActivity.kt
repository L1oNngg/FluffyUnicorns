package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.api.AccountAPI
import com.example.fluffyunicorns.api.Account_RetrofitClient
import com.example.fluffyunicorns.model.AccountResponse
import com.example.fluffyunicorns.model.EditAccountRequest
import com.example.fluffyunicorns.model.EditAccountResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUserAccountActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var idNumberEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_edit_account)

        // Initialize UI elements
        firstNameEditText = findViewById(R.id.Firstname)
        lastNameEditText = findViewById(R.id.Lastname)
        emailEditText = findViewById(R.id.email)
        phoneEditText = findViewById(R.id.phone)
        idNumberEditText = findViewById(R.id.IDNumber)

        fetchAccountDetails()

        // Back button listener
        val backIcon: ImageButton = findViewById(R.id.backIcon)
        backIcon.setOnClickListener {
            navigateBackToAccount()
        }

        // Save button listener
        val saveButton: Button = findViewById(R.id.btnBack)
        saveButton.setOnClickListener {
            updateAccountDetails()
        }
    }

    private fun navigateBackToAccount() {
        val intent = Intent(this, UserAccountActivity::class.java)
        startActivity(intent)
    }

    private fun fetchAccountDetails() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val customerID = sharedPreferences.getInt("customerID", -1)

        if (customerID == -1) {
            Toast.makeText(this, "User information not found.", Toast.LENGTH_SHORT).show()
            return
        }

        // Get an instance of the API service
        val accountAPI = Account_RetrofitClient.instance.create(AccountAPI::class.java)
        val call = accountAPI.getAccountDetails(customerID)

        // Make the API call
        call.enqueue(object : Callback<AccountResponse> {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
                if (response.isSuccessful) {
                    val accountData = response.body()?.data
                    if (accountData != null) {
                        // Populate the UI with account details as hints
                        firstNameEditText.hint = accountData.FirstName ?: "N/A"
                        lastNameEditText.hint = accountData.LastName ?: "N/A"
                        emailEditText.hint = accountData.Email ?: "N/A"
                        idNumberEditText.hint = accountData.IDNumber?.trim() ?: "N/A" // Safely handle null for IDNumber
                        phoneEditText.hint = accountData.Phone ?: "N/A"
                    }
                } else {
                    Toast.makeText(this@EditUserAccountActivity, "Failed to load user information", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                Toast.makeText(this@EditUserAccountActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun updateAccountDetails() {
        // Retrieve customerID from SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val customerID = sharedPreferences.getInt("customerID", -1)

        if (customerID == -1) {
            Toast.makeText(this, "Invalid customer ID. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        // Collect user input
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val idNumber = idNumberEditText.text.toString().trim()

        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || idNumber.isEmpty()) {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
            return
        }

        // Create EditAccountRequest object
        val editAccountRequest = EditAccountRequest(
            AccountID = 0, // Replace with actual account ID if needed
            CustomerID = customerID,
            FirstName = firstName,
            MiddleName = null, // Optional field
            LastName = lastName,
            Username = email,
            Status = "", // Adjust if status is required
            DateOfBirth = "", // Adjust if required
            Gender = "", // Adjust if required
            Email = email,
            Phone = phone,
            Address = "", // Adjust if address is required
            IDNumber = idNumber,
            RewardPoints = 0 // Adjust if reward points are used
        )

        // API call to update account
        val accountAPI = Account_RetrofitClient.instance.create(AccountAPI::class.java)
        val call = accountAPI.updateAccountDetails(customerID, editAccountRequest)

        call.enqueue(object : Callback<EditAccountResponse> {
            override fun onResponse(
                call: Call<EditAccountResponse>,
                response: Response<EditAccountResponse>
            ) {
                if (response.isSuccessful) {
                    val editResponse = response.body()
                    if (editResponse != null && editResponse.success) {
                        Toast.makeText(this@EditUserAccountActivity, "Account updated successfully!", Toast.LENGTH_SHORT).show()
                        navigateBackToAccount()
                    } else {
                        Toast.makeText(this@EditUserAccountActivity, "Failed to update account: ${editResponse?.data}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@EditUserAccountActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EditAccountResponse>, t: Throwable) {
                Toast.makeText(this@EditUserAccountActivity, "Failed to connect: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
