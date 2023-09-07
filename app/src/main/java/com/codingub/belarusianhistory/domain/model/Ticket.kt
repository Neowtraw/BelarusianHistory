package com.codingub.belarusianhistory.domain.model

import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves

data class Ticket(
    val id: Int,
    val name: String,
    var isPassed: Int,
    val questionList: List<TicketQuestion>,
    val achievement: TicketAchieves
)
