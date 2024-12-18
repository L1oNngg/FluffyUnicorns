package com.example.fluffyunicorns.model

data class BookingResponse(
    val success: Boolean,
    val data: List<BookingHistory>
)

data class BookingHistory(
    val BookingID: Int,
    val PaymentStatus: String,
    val BookingDate: String,
    val CheckInDate: String,
    val CheckOutDate: String,
    val TypeName: String,
    val BasePrice: String,
    val total_guests: String,
    val total_nights: Int,
    val services: List<ServiceHistory>,
    val total_price: Int
)

data class ServiceHistory(
    val UnitPrice: Int?,
    val ServiceName: String?
)
