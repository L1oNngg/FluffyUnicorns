package com.example.fluffyunicorns.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    val name: String,
    val price: Double,
    val iconResId: Int,
    var isSelected: Boolean = false
) : Parcelable

