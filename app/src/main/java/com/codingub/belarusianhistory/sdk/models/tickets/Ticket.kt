package com.codingub.belarusianhistory.sdk.models.tickets

import com.codingub.belarusianhistory.sdk.models.achieves.Achieve
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Ticket(
    val id: String,
    val name: String,
    val timer: Long, //for timer in ticket
    var tqs: List<TicketQuestion> = emptyList(),
    val achievement: Achieve? = null
)

