package com.codingub.belarusianhistory.domain.model

data class Ticket(
    val id: Int,
    val name: String,
    var isPassed: Boolean,
    val questionList: List<TicketQuestion>,
    val achievementList: List<Achievement>
)
