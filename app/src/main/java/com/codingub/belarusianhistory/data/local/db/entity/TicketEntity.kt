package com.codingub.belarusianhistory.data.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.codingub.belarusianhistory.data.local.db.entity.achieves.TicketAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.ticket.TicketRef
import com.codingub.belarusianhistory.domain.model.Ticket

data class TicketEntity(
    @Embedded val ticket: TicketRef,
    @Relation(
        parentColumn = "ticketId",
        entityColumn = "ticketId"
    )
    val achieves: TicketAchievesRef,
    @Relation(
        parentColumn = "ticketId",
        entityColumn = "ticketId"
    )
    val questions: List<TicketQuestionEntity>? = null
){
    fun toTicket() : Ticket{
        return Ticket(
            id = ticket.ticketId,
            name = ticket.ticketName,
            isPassed = ticket.isPassed,
            questionList = questions!!.map { it.toTicketQuestion() },
            achievement = achieves.toTicketAchieves()
        )
    }
}
