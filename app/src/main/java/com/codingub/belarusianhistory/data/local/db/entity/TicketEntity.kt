package com.codingub.belarusianhistory.data.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.codingub.belarusianhistory.data.local.db.entity.achieves.TicketAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.questions.TicketQuestionRef
import com.codingub.belarusianhistory.data.local.db.entity.ticket.TicketRef

data class TicketEntity(
    @Embedded val ticket: TicketRef,
    @Relation(
        parentColumn = "ticketId",
        entityColumn = "ticketId",
        entity = TicketAchievesRef::class
    )
    val achieves: TicketAchievesRef,
    @Relation(
        parentColumn = "ticketId",
        entityColumn = "ticketId",
        entity = TicketQuestionRef::class
    )
    val questions: List<TicketQuestionRef>
)
