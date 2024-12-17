package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.api.Account_RetrofitClient
import com.example.fluffyunicorns.model.RegisterRequest
import com.example.fluffyunicorns.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_register)

        val firstNameInput: EditText = findViewById(R.id.firstname)
        val lastNameInput: EditText = findViewById(R.id.lastname)
        val emailInput: EditText = findViewById(R.id.email)
        val phoneInput: EditText = findViewById(R.id.phone)
        val passwordInput: EditText = findViewById(R.id.password)
        val confirmPasswordInput: EditText = findViewById(R.id.confirm_password)
        val signInLink: TextView = findViewById(R.id.sign_in_link)
        val registerButton: Button = findViewById(R.id.register_button)

        signInLink.setOnClickListener {
            // Redirect to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            // Get user input
            val firstName = firstNameInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            // Validate input
            if (validateInput(firstName, lastName, email, phone, password, confirmPassword)) {
                registerUser(firstName, lastName, email, phone, password)
            }
        }
    }

    private fun validateInput(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
            phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
        ) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!phone.matches(Regex("\\d{10,15}"))) { // Adjust regex based on phone number rules
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String
    ) {
        // Create request object
        val registerRequest = RegisterRequest(
            FirstName = firstName,
            LastName = lastName,
            Username = email,
            Phone = phone,
            Password = password
        )

        // Send data to the API
        Account_RetrofitClient.instance.registerUser(registerRequest)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Toast.makeText(
                            this@RegisterActivity,
                            apiResponse?.message ?: "Registration successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // Close this activity after registration
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Failed: ${response.errorBody()?.string()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
