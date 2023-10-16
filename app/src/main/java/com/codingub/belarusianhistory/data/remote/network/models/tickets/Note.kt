package com.codingub.belarusianhistory.data.remote.network.models.tickets

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Note(
    @Json(name = "id") val id : String,
    @Json(name = "info") val info: String,
    @Json(name = "ticketId") val ticketId: String
)
