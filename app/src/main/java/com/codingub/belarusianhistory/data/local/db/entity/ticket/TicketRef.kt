package com.codingub.belarusianhistory.data.local.db.entity.ticket

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ticket")
data class TicketRef(
    @PrimaryKey(autoGenerate = false)
    val ticketId: Int,
    val ticketName: String,
    var isPassed: Int
)