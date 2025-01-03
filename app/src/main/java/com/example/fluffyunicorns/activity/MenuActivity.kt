package com.example.fluffyunicorns.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.model.Room
import com.example.fluffyunicorns.adapter.RoomAdapter
import com.example.fluffyunicorns.api.AccountAPI
import com.example.fluffyunicorns.api.RetrofitClient
import com.example.fluffyunicorns.api.RoomAPI
import com.example.fluffyunicorns.model.AccountResponse
import com.example.fluffyunicorns.model.RoomDetails
import com.example.fluffyunicorns.model.RoomResponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class MenuActivity : AppCompatActivity() {

    lateinit var TextDate1: EditText
    lateinit var dateBtn1: ImageButton
    lateinit var TextDate2: EditText
    lateinit var dateBtn2: ImageButton
    private val calendar = Calendar.getInstance()

    private lateinit var tvGreeting: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RoomAdapter
    private val roomList = mutableListOf<Room>() // Dummy data for RecyclerView

    lateinit var etSearch: EditText
    lateinit var btnSearch: Button
    lateinit var filterBtn: ImageButton

    private var adults = 1
    private var children = 0
    private var rooms = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_menu)

        val iconSettings: ImageView = findViewById(R.id.iconSettings)

        iconSettings.setOnClickListener {
            val intent = Intent(this, SettingsTabActivity::class.java)
            startActivity(intent)
        }

        val iconHistory: ImageView = findViewById(R.id.iconHistory)

        iconHistory.setOnClickListener {
            val intent = Intent(this, HistoryTabActivity::class.java)
            startActivity(intent)
        }

        // API
        tvGreeting = findViewById(R.id.tvGreeting)

        // Retrieve customerID from SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val customerID = sharedPreferences.getInt("customerID", -1)

        if (customerID != -1) {
            // Fetch user account details if customerID is valid
            fetchAccountDetails(customerID)
        } else {
            Toast.makeText(this, "User information not found", Toast.LENGTH_SHORT).show()
        }

        // Initialize UI components
        TextDate1 = findViewById(R.id.editTextDate1)
        dateBtn1 = findViewById(R.id.date_btn1)
        TextDate2 = findViewById(R.id.editTextDate2)
        dateBtn2 = findViewById(R.id.date_btn2)
        filterBtn = findViewById(R.id.filterbtn)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.rvRecommendations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RoomAdapter(roomList,
            onBookClick = { room ->
                val intent = Intent(this, CustomerInformationActivity::class.java)
                intent.putExtra("ROOM_ID", room.id)
                startActivity(intent)
            },
            onViewClick = { room ->
                val intent = Intent(this, BookingActivity::class.java)
                intent.putExtra("ROOM_ID", room.id)
                startActivity(intent)
            }
        )
        recyclerView.adapter = adapter

        // Fetch room data from API
        fetchRooms()

        // Set up search components
        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)

        // Load initial data into RecyclerView
        loadDummyData()

        // Search functionality
        btnSearch.setOnClickListener {
            val query = (adults + children)
            if (query > 0) {
                performSearch(query)
            } else {
                Toast.makeText(this, "Please enter a valid search query!", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up date pickers
        dateBtn1.setOnClickListener {
            showDatePicker1()
        }
        dateBtn2.setOnClickListener {
            showDatePicker2()
        }

        // Set up BottomSheetDialog for filter button
        filterBtn.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.dialog_filter, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetView)

        val tvSummary = bottomSheetView.findViewById<TextView>(R.id.tvSummary)
        val btnMinusAdults = bottomSheetView.findViewById<Button>(R.id.btnMinusAdults)
        val btnPlusAdults = bottomSheetView.findViewById<Button>(R.id.btnPlusAdults)
        val tvAdults = bottomSheetView.findViewById<TextView>(R.id.tvAdults)

        val btnMinusChildren = bottomSheetView.findViewById<Button>(R.id.btnMinusChildren)
        val btnPlusChildren = bottomSheetView.findViewById<Button>(R.id.btnPlusChildren)
        val tvChildren = bottomSheetView.findViewById<TextView>(R.id.tvChildren)

        val btnMinusRooms = bottomSheetView.findViewById<Button>(R.id.btnMinusRooms)
        val btnPlusRooms = bottomSheetView.findViewById<Button>(R.id.btnPlusRooms)
        val tvRooms = bottomSheetView.findViewById<TextView>(R.id.tvRooms)

        val btnDone = bottomSheetView.findViewById<Button>(R.id.btnDone)

        // Initialize values
        tvAdults.text = adults.toString()
        tvChildren.text = children.toString()
        tvRooms.text = rooms.toString()
        updateSummary(tvSummary)

        btnMinusAdults.setOnClickListener {
            adults = (adults - 1).coerceAtLeast(0)
            tvAdults.text = adults.toString()
            updateSummary(tvSummary)
        }

        btnPlusAdults.setOnClickListener {
            adults++
            tvAdults.text = adults.toString()
            updateSummary(tvSummary)
        }

        btnMinusChildren.setOnClickListener {
            children = (children - 1).coerceAtLeast(0)
            tvChildren.text = children.toString()
            updateSummary(tvSummary)
        }

        btnPlusChildren.setOnClickListener {
            children++
            tvChildren.text = children.toString()
            updateSummary(tvSummary)
        }

        btnMinusRooms.setOnClickListener {
            rooms = (rooms - 1).coerceAtLeast(0)
            tvRooms.text = rooms.toString()
            updateSummary(tvSummary)
        }

        btnPlusRooms.setOnClickListener {
            rooms++
            tvRooms.text = rooms.toString()
            updateSummary(tvSummary)
        }

        btnDone.setOnClickListener {
            etSearch.setText("$adults Adults, $children Children, $rooms Room")
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateSummary(tvSummary: TextView) {
        tvSummary.text = "$adults Adults, $children Children, $rooms Room"
    }

    private fun loadDummyData() {
        roomList.clear()
        roomList.add(Room(1,"Deluxe Room - King Bed", "120", 2))
        roomList.add(Room(2,"Family Suite - 2 Beds", "200", 4))
        roomList.add(Room(3,"Standard Room - Queen Bed", "80", 2))
        adapter.notifyDataSetChanged()
    }

    private fun performSearch(query: Int) {
        val filteredList = roomList.filter { it.capacity >= query }
        adapter.updateRooms(filteredList)

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No rooms found for capacity '$query'", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePicker1() {
        val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            TextDate1.text = Editable.Factory.getInstance().newEditable(formattedDate)
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showDatePicker2() {
        val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            TextDate2.text = Editable.Factory.getInstance().newEditable(formattedDate)
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun fetchRooms() {
        val roomAPI = RetrofitClient.createRoomService()
        val call = roomAPI.getRoom()

        call.enqueue(object : Callback<RoomResponse> {
            override fun onResponse(call: Call<RoomResponse>, response: Response<RoomResponse>) {
                if (response.isSuccessful) {
                    val roomData = response.body()
                    if (roomData != null) {
                        roomList.clear()
                        // Map RoomDetails to Room, using only name, price, and capacity
                        roomList.addAll(roomData.map())
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@MenuActivity, "Failed to load room data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun fetchAccountDetails(customerID: Int) {
        // Get an instance of the API service
        val accountAPI = RetrofitClient.instance.create(AccountAPI::class.java)
        val call = accountAPI.getAccountDetails(customerID)

        // Make the API call
        call.enqueue(object : Callback<AccountResponse> {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
                if (response.isSuccessful) {
                    val accountData = response.body()?.data
                    if (accountData != null) {
                        // Populate the UI with account details, handle potential nulls
                        tvGreeting.text = ("Hello, \n" + accountData.FirstName + " " + accountData.LastName) ?: "N/A"
                    }
                } else {
                    Toast.makeText(this@MenuActivity, "Failed to load user information", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}