package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingub.belarusianhistory.data.local.db.entity.PracticeQuestionEntity
import com.codingub.belarusianhistory.data.local.db.entity.questions.PracticeQuestionRef


@Dao
interface PracticeQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPracticeQuestion(question: PracticeQuestionRef)

    @Query("DELETE FROM 'PracticeQuestion' WHERE pqId=:questionId")
    suspend fun deletePracticeQuestionById(questionId: Int)
}