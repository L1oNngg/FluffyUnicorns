package com.example.fluffyunicorns.model

import com.google.gson.*
import java.lang.reflect.Type

data class RegisterRequest(
    val FirstName: String,
    val LastName: String,
    val Username: String,
    val Phone: String,
    val Password: String
)

// Data class for the expected object
data class Data(val username: String)

// ApiResponse with 'data' as a flexible type
data class RegisterResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: Any? // Accepts both string and object
)

// Custom deserializer for the 'data' field
class DataDeserializer : JsonDeserializer<Any> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any? {
        return when {
            json?.isJsonObject == true -> context?.deserialize(json, Data::class.java)
            json?.isJsonPrimitive == true -> json.asString
            else -> null
        }
    }
}
