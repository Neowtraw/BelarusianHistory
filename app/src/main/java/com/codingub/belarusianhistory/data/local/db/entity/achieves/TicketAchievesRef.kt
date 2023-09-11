package com.codingub.belarusianhistory.data.local.db.entity.achieves

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves

@Entity(tableName = "TicketAchieves")
data class TicketAchievesRef (
    @PrimaryKey(autoGenerate = false)
    val achievementId: Int,
    val achievementName: String,
    val achievementInfo: String,
    var isPassed: Int,
    val ticketId: Int //один к одному с Ticket
){
    fun toTicketAchieves() : TicketAchieves {
        return TicketAchieves(
            id = achievementId,
            name = achievementName,
            info = achievementInfo,
            isPassed = isPassed,
            ticketId = ticketId
        )
    }
}