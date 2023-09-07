package com.codingub.belarusianhistory.data.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.codingub.belarusianhistory.data.local.db.entity.questions.PracticeQuestionRef
import com.codingub.belarusianhistory.data.local.db.entity.questions.TicketQuestionRef
import com.codingub.belarusianhistory.domain.model.TicketQuestion


data class TicketQuestionEntity(
    @Embedded val tQuestion: TicketQuestionRef,
    @Relation(
        parentColumn = "tqId",
        entityColumn = "tqId",
        entity = PracticeQuestionRef::class
    ) val practiceList : List<PracticeQuestionRef>
){
    fun toTicketQuestion(): TicketQuestion{

        return TicketQuestion(
            id = tQuestion.tqId,
            name = tQuestion.tqName,
            info = tQuestion.tqInfo,
            isPassed = tQuestion.isPassed,
            practiceList = emptyList()
        )
    }
}
