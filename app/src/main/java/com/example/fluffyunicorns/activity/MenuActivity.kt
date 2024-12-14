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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.Room
import com.example.fluffyunicorns.adapter.RoomAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar
import java.util.Locale

class MenuActivity : AppCompatActivity() {

    lateinit var TextDate1: EditText
    lateinit var dateBtn1: ImageButton
    lateinit var TextDate2: EditText
    lateinit var dateBtn2: ImageButton
    private val calendar = Calendar.getInstance()

    // RecyclerView and Search Elements
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RoomAdapter
    private val roomList = mutableListOf<Room>() // Dummy data for RecyclerView

    lateinit var etSearch: EditText
    lateinit var btnSearch: Button
    lateinit var filterBtn: ImageButton

    private var adults = 1
    private var children = 0
    private var infants = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_menu)

        // Initialize UI components
        TextDate1 = findViewById(R.id.editTextDate1)
        dateBtn1 = findViewById(R.id.date_btn1)
        TextDate2 = findViewById(R.id.editTextDate2)
        dateBtn2 = findViewById(R.id.date_btn2)
        filterBtn = findViewById(R.id.filterbtn)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.rvRecommendations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RoomAdapter(roomList)
        recyclerView.adapter = adapter

        // Set up search components
        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)

        // Load initial data into RecyclerView
        loadDummyData()

        // Search functionality
        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {
                Toast.makeText(this, "Please enter a search query!", Toast.LENGTH_SHORT).show()
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

        val LoginBtn: ImageView = findViewById(R.id.iconSettings)

        LoginBtn.setOnClickListener {
            val intent = Intent(this, SettingsTabActivity::class.java)
            startActivity(intent)
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

        val btnMinusInfants = bottomSheetView.findViewById<Button>(R.id.btnMinusInfants)
        val btnPlusInfants = bottomSheetView.findViewById<Button>(R.id.btnPlusInfants)
        val tvInfants = bottomSheetView.findViewById<TextView>(R.id.tvInfants)

        val btnDone = bottomSheetView.findViewById<Button>(R.id.btnDone)

        // Initialize values
        tvAdults.text = adults.toString()
        tvChildren.text = children.toString()
        tvInfants.text = infants.toString()
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

        btnMinusInfants.setOnClickListener {
            infants = (infants - 1).coerceAtLeast(0)
            tvInfants.text = infants.toString()
            updateSummary(tvSummary)
        }

        btnPlusInfants.setOnClickListener {
            infants++
            tvInfants.text = infants.toString()
            updateSummary(tvSummary)
        }

        btnDone.setOnClickListener {
            etSearch.setText("$adults Người lớn, $children Trẻ em, $infants Phòng")
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateSummary(tvSummary: TextView) {
        tvSummary.text = "$adults Người lớn, $children Trẻ em, $infants Em bé"
    }

    private fun loadDummyData() {
        roomList.clear()
        roomList.add(Room("Deluxe Room - King Bed", "120", 2))
        roomList.add(Room("Family Suite - 2 Beds", "200", 4))
        roomList.add(Room("Standard Room - Queen Bed", "80", 2))
        adapter.notifyDataSetChanged()
    }

    private fun performSearch(query: String) {
        val filteredList = roomList.filter { it.name.contains(query, ignoreCase = true) }
        adapter.updateRooms(filteredList)

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No rooms found for '$query'", Toast.LENGTH_SHORT).show()
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
}