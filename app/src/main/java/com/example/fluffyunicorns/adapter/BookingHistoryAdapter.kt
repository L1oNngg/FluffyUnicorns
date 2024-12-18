package com.example.fluffyunicorns.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.activity.BookingDetailsActivity
import com.example.fluffyunicorns.model.BookingHistory

class BookingHistoryAdapter(private val context: Context, private val bookings: List<BookingHistory>) :
    RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder>() {

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeNameTextView: TextView = itemView.findViewById(R.id.tvRoomName)
        val checkOutDateTextView: TextView = itemView.findViewById(R.id.tvDate)
        val imageButton: ImageButton = itemView.findViewById(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.typeNameTextView.text = booking.TypeName
        holder.checkOutDateTextView.text = booking.CheckOutDate.substring(0, 10)
        holder.imageButton.setOnClickListener {
            val intent = Intent(context, BookingDetailsActivity::class.java)
            context.startActivity(intent)
        }
    }
    //test

    override fun getItemCount() = bookings.size
}