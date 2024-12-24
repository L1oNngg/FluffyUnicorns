package com.example.fluffyunicorns.model

data class RoomDetails(
    val RoomID: Int,
    val RoomNumber: String,
    val Status: String,
    val TypeName: String,
    val BasePrice: String,
    val MaxOccupancy: Int,
    val Area: String,
    val amenities: List<String>,
    val images: List<String>
)

data class RoomDetailsResponse(
    val success: Boolean,
    val data: RoomDetails
)

data class RoomResponse(
    val success: Boolean,
    val data: List<RoomDetails>
) {
    fun map(): Collection<Room> {
        return data.map { roomDataItem ->
            Room(
                id = roomDataItem.RoomID,
                name = "${roomDataItem.TypeName} ${roomDataItem.RoomNumber}",
                price = roomDataItem.BasePrice,
                capacity = roomDataItem.MaxOccupancy
            )
        }
    }
}