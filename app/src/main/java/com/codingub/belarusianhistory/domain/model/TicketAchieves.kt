package com.codingub.belarusianhistory.domain.model

data class TicketAchieves(
    val id: Int,
    val name: String,
    val info: String,
    var isPassed: Boolean,
    val ticketId: Long
)
