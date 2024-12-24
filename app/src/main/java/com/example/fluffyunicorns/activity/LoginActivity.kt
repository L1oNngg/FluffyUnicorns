package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.api.RetrofitClient
import com.example.fluffyunicorns.model.LoginRequest
import com.example.fluffyunicorns.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_login)

        // Link UI components to variables
        val usernameInput: EditText = findViewById(R.id.usernameInput)
        val passwordInput: EditText = findViewById(R.id.passwordInput)
        val loginBtn: Button = findViewById(R.id.LoginBtn)
        val tvRegister: TextView = findViewById(R.id.tvRegister)
        val tvForget: TextView = findViewById(R.id.tvForget)

        // Navigate to the RegisterActivity when the "Register" TextView is clicked
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Navigate to the ForgotPasswordActivity when the "Forgot Password" TextView is clicked
        tvForget.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        // Handle the Login button click
        loginBtn.setOnClickListener {
            // Get the username and password from the input fields
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            // Validate that the inputs are not empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a LoginRequest object
            val loginRequest = LoginRequest(username, password)

            // Use Retrofit to send a login request to the API
            val accountAPI = RetrofitClient.createAccountService()
            accountAPI.loginUser(loginRequest)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            val loginResponse = response.body()!!

                            if (loginResponse.token.isNotEmpty()) {
                                val customerID = loginResponse.customerID // Get the customerID from the response

                                // Save the token and customerID in SharedPreferences
                                val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("token", loginResponse.token)
                                editor.putInt("customerID", customerID) // Save customerID
                                editor.apply()

                                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                                // Navigate to the UserAccountActivity
                                val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                                startActivity(intent)

                                // Close the LoginActivity to prevent back navigation
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "Login failed: Invalid credentials", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
