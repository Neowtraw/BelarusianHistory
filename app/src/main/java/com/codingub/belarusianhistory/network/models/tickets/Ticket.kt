package com.codingub.belarusianhistory.network.models.tickets

import com.codingub.belarusianhistory.network.models.achieves.Achieve
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ticket(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "timer") val timer: Long, //for timer in ticket
    @Json(name = "questions") var questions: List<TicketQuestion> = emptyList(),
    @Json(name = "achievement") val achievement: Achieve? = null
)

