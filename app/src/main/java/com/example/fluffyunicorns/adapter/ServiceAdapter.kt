package com.example.fluffyunicorns.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fluffyunicorns.R
import com.example.fluffyunicorns.model.Service

class ServiceAdapter(
    private val serviceList: List<Service>,
    private val onServiceSelected: (Service) -> Unit // Callback for selected services
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imageViewServiceIcon)
        val name: TextView = view.findViewById(R.id.textViewServiceName)
        val checkBox: CheckBox = view.findViewById(R.id.checkBoxService)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ui_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = serviceList[position]

        // Bind data
        holder.name.text = service.name
        holder.checkBox.isChecked = service.isSelected

        // Bind the image resource to the ImageView
        holder.icon.setImageResource(service.iconResId)

        // Set listeners
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            service.isSelected = isChecked
            onServiceSelected(service) // Notify parent activity or fragment
        }
    }

    override fun getItemCount(): Int = serviceList.size
}

