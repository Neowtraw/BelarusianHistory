package com.codingub.belarusianhistory.data.local.db.entity.questions

import androidx.room.Entity

//new realisation for pq

@Entity
data class AnswerRef (
    val answerId: Int,
    val pqId: Int
)