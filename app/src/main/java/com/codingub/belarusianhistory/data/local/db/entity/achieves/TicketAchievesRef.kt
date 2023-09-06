package com.codingub.belarusianhistory.data.local.db.entity.achieves

import androidx.room.Entity
import com.codingub.belarusianhistory.domain.model.TicketAchieves

@Entity(tableName = "TicketAchieves")
data class TicketAchievesRef (
    val achievementId: Int,
    val achievementName: String,
    val achievementInfo: String,
    var isPassed: Boolean,
    val ticketId: Long //один к одному с Ticket
){
    fun toTicketAchieves() : TicketAchieves{
        return TicketAchieves(
            id = achievementId,
            name = achievementName,
            info = achievementInfo,
            isPassed = isPassed,
            ticketId = ticketId
        )
    }
}