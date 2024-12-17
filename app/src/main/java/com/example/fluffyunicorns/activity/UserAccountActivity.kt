package com.example.fluffyunicorns.activity

import android.os.Bundle
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
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var email: TextView
    private lateinit var idNumber: TextView
    private lateinit var phone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_account)

        // Initialize views
        firstName = findViewById(R.id.Firstname)
        lastName = findViewById(R.id.Lastname)
        email = findViewById(R.id.email)
        idNumber = findViewById(R.id.IDNumber)
        phone = findViewById(R.id.phone)

        // Fetch user data
        fetchAccountDetails(1)
    }

    private fun fetchAccountDetails(accountID: Int) {
        val accountAPI = Account_RetrofitClient.instance.create(AccountAPI::class.java)
        val call = accountAPI.getAccountDetails(accountID)

        call.enqueue(object : Callback<AccountResponse> {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
                if (response.isSuccessful) {
                    val accountData = response.body()?.data
                    if (accountData != null) {
                        // Populate UI with data
                        firstName.text = accountData.firstName
                        lastName.text = accountData.lastName
                        email.text = accountData.email
                        idNumber.text = accountData.idNumber
                        phone.text = accountData.phone
                    }
                } else {
                    Toast.makeText(this@UserAccountActivity, "Failed to fetch account details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                Toast.makeText(this@UserAccountActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
