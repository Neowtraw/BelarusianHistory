package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.codingub.belarusianhistory.data.local.db.entity.TicketQuestionEntity


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

    @Query("UPDATE 'TicketQuestion' SET isPassed=:passed WHERE tqId=:id")
    suspend fun updateTicketQuestionPassed(id: Int, passed: Int)

}