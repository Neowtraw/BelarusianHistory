package com.codingub.belarusianhistory.data.local.db.entity.questions

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TicketQuestion")
data class TicketQuestionRef(
    @PrimaryKey(autoGenerate = false)
    val tqId : Int,
    val tqName : String,
    val tqInfo: String,
    var isPassed: Int,
    val ticketId: Int //много к одному c Ticket
)