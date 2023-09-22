package com.codingub.belarusianhistory.data.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.codingub.belarusianhistory.data.local.db.entity.achieves.PracticeAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.questions.AnswerRef
import com.codingub.belarusianhistory.data.local.db.entity.questions.PracticeQuestionRef
import com.codingub.belarusianhistory.domain.model.PracticeQuestion


data class PracticeQuestionEntity(

    //один к одному
    @Embedded val pQuestion: PracticeQuestionRef,
    @Relation(
        parentColumn = "pqId",
        entityColumn = "pqId",
        entity = PracticeAchievesRef::class
    ) val achievementId : PracticeAchievesRef,
    @Relation(
        parentColumn = "pqId",
        entityColumn = "pqId",
        entity = AnswerRef::class
    ) val answers : List<AnswerRef>
){
    fun toPracticeQuestion() : PracticeQuestion{
        return PracticeQuestion(
            id = pQuestion.pqId,
            taskType = pQuestion.taskType,
            name = pQuestion.pqName,
            info = pQuestion.pqInfo,
            answers = answers.map{
                 it.toAnswer()
            },
            achievement = achievementId.toPracticeAchieves()
        )
    }
}