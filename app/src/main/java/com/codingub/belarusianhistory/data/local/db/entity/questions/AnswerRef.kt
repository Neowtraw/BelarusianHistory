package com.codingub.belarusianhistory.data.local.db.entity.questions

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingub.belarusianhistory.domain.model.Answer

//new realisation for pq

@Entity(tableName = "Answer")
data class AnswerRef (
    @PrimaryKey
    val answerId: Int,
    val answerName: String,
    val isTrue: Int,
    val pqId: Int
){
    fun toAnswer() : Answer {
        return Answer(
            answerId = answerId,
            answerName = answerName,
            isTrue = isTrue,
            pqId = pqId
        )
    }
}