package com.codingub.belarusianhistory.domain.model.Achieves

data class TicketAchieves(
    val id: Int,
    override val name: String,
    override val info: String,
    override var isPassed: Int,
    val ticketId: Int
) : Achieve
