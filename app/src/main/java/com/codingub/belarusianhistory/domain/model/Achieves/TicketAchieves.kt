package com.codingub.belarusianhistory.domain.model.Achieves

data class TicketAchieves(
    override val id: Int,
    override val name: String,
    override val info: String,
    override var isPassed: Int,
    val ticketId: Int
) : Achieve{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TicketAchieves) return false
        return true
    }
}
