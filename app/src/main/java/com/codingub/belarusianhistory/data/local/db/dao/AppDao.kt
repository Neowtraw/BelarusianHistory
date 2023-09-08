package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.codingub.belarusianhistory.data.local.db.entity.PracticeQuestionEntity
import com.codingub.belarusianhistory.data.local.db.entity.TicketEntity
import com.codingub.belarusianhistory.data.local.db.entity.TicketQuestionEntity
import com.codingub.belarusianhistory.data.local.db.entity.achieves.PracticeAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.achieves.TicketAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.ticket.TicketRef
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao{


    //необходимо для "Практика"
    @Transaction
    @Query("SELECT * FROM TicketQuestion")
    suspend fun getAllTicketQuestions() : List<TicketQuestionEntity>

    //Получение отдельно теоретических достижений
    @Transaction
    @Query("SELECT * FROM TicketAchieves")
    fun getAllTicketAchieves() : Flow<List<TicketAchievesRef>>

    //Получение отдельно практических достижений
    @Transaction
    @Query("SELECT * FROM PracticeAchieves")
    fun getAllPracticeAchieves() : Flow<List<PracticeAchievesRef>>

    //Необходимо для "Теория"
    @Transaction
    @Query("SELECT * FROM Ticket")
    suspend fun getAllTickets() : List<TicketEntity>

    //чтение определенного Ticket
    @Transaction
    @Query("SELECT * FROM Ticket WHERE ticketId = :id")
    fun getTicketById(id: Int) : Flow<TicketEntity>

    //прохождение практики определенного TicketQuestion
    @Transaction
    @Query("SELECT * FROM TicketQuestion WHERE tqId = :id")
    suspend fun getTicketQuestionsById(id: Int) : TicketQuestionEntity


//    //для обновления данных о прохождении достижений/билетов/практики
//    @Update
//    suspend fun setTicketAchievePassed(vararg achieves: TicketAchievesRef)
//
//    @Update
//    fun setPracticeAchievePassed(vararg achieves: PracticeAchievesRef)
//
//    @Update
//    fun setTicketPassed(vararg tickets: TicketRef)
//
//    @Update
//    fun setTicketQuestionPassed(vararg achieves: PracticeAchievesRef)


}