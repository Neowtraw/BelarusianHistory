package com.codingub.belarusianhistory.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingub.belarusianhistory.sdk.AchieveType

@Entity
data class ResultEntity(
    @PrimaryKey val id: String,
    val type : AchieveType,
    val answer: Any?,
    val achieveId: String, //id of object (Ticket/Practice)
    val userLogin: String,
    val isSynchronized: Boolean
)
