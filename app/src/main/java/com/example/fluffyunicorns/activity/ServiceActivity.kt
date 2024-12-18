package com.example.fluffyunicorns.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.adapter.ServiceAdapter
import com.example.fluffyunicorns.model.Service

class ServiceActivity : AppCompatActivity() {

    private lateinit var services: List<Service>
    private lateinit var adapter: ServiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_service)

        // Initialize the service list
        services = listOf(
            Service("Laundry", 20.0, R.drawable.ic_laundry),
            Service("In-room dining", 30.0, R.drawable.ic_food_dish),
            Service("Spa treatments", 50.0, R.drawable.ic_spa),
            Service("Airport shuttle", 30.0, R.drawable.ic_bus),
            Service("Bike rentals", 15.0, R.drawable.ic_bicycle)
        )

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.serviceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // Set the layout manager
        adapter = ServiceAdapter(services) {}
        recyclerView.adapter = adapter // Attach the adapter

        // Handle Next button click
        findViewById<Button>(R.id.nextBtn).setOnClickListener {
            val selectedServices = services.filter { it.isSelected }
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putParcelableArrayListExtra("selectedServices", ArrayList(selectedServices))
            startActivity(intent)
        }

        // Handle Back button click
        findViewById<ImageButton>(R.id.backIcon).setOnClickListener {
            finish() // Close this activity and return to the previous one
        }
    }
}
