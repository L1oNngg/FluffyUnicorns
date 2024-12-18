package com.example.fluffyunicorns.adapter

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.model.Room

class RoomAdapter(private var rooms: List<Room>) :
    RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val roomName: TextView = view.findViewById(R.id.tvRoomName)
        val roomPrice: TextView = view.findViewById(R.id.tvDate)
        val roomCapacity: TextView = view.findViewById(R.id.tvRoomCapacity)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): RoomViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.roomName.text = room.name
        holder.roomPrice.text = room.price
        holder.roomCapacity.text = room.capacity.toString()
    }

    override fun getItemCount(): Int = rooms.size

    fun updateRooms(newRooms: List<Room>) {
        rooms = newRooms
        notifyDataSetChanged()
    }
}