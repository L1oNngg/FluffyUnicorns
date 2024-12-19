package com.example.fluffyunicorns.model

data class RoomResponse(
    val success: Boolean,
    val data: List<RoomData>
)

data class RoomData(
    val RoomID: Int,
    val RoomNumber: String,
    val Status: String,
    val TypeName: String,
    val BasePrice: String,
    val MaxOccupancy: Int,
    val Area: String,
    val Amenities: Amenity,
    val Images: Image
)

data class Amenity(
    val name: String
)

data class Image(
    val url: String
)
