package com.codingub.belarusianhistory.data.remote.network.models.tickets

import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Ticket(
    val id: String,
    val name: String,
    val timer: Long, //for timer in ticket
    var questions: List<TicketQuestion> = emptyList(),
    val achievement: Achieve? = null
)

