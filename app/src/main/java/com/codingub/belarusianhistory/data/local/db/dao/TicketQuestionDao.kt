package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.codingub.belarusianhistory.data.local.db.entity.TicketQuestionEntity
import com.codingub.belarusianhistory.data.local.db.entity.questions.TicketQuestionRef


@Dao
interface TicketQuestionDao {


    //необходимо для "Практика"
    @Transaction
    @Query("SELECT * FROM TicketQuestion")
    suspend fun getAllTicketQuestions() : List<TicketQuestionEntity>

    //прохождение практики определенного TicketQuestion
    @Transaction
    @Query("SELECT * FROM TicketQuestion WHERE tqId = :id")
    suspend fun getTicketQuestionsById(id: Int) : TicketQuestionEntity


    @Update
    suspend fun setTicketQuestionPassed(ticket : TicketQuestionRef)
}