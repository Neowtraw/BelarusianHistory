package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.codingub.belarusianhistory.data.local.db.entity.TicketEntity
import com.codingub.belarusianhistory.data.local.db.entity.TicketQuestionEntity
import com.codingub.belarusianhistory.data.local.db.entity.achieves.PracticeAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.achieves.TicketAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.questions.TicketQuestionRef
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
    suspend fun getAllTicketAchieves() : List<TicketAchievesRef>

    //Получение отдельно практических достижений
    @Transaction
    @Query("SELECT * FROM PracticeAchieves")
    suspend fun getAllPracticeAchieves() : List<PracticeAchievesRef>

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

    //получение количества пройденных билетов
    @Transaction
    @Query("SELECT * FROM TicketAchieves WHERE isPassed = :isPassed")
    suspend fun getTicketAchievesByPassed(isPassed: Int) : List<TicketAchievesRef>

    //получение количества пройденной практики
    @Transaction
    @Query("SELECT * FROM PracticeAchieves WHERE isPassed = :isPassed")
    suspend fun getPracticeAchievesByPassed(isPassed: Int) : List<PracticeAchievesRef>

    //для обновления данных

    @Update
    suspend fun setTicketAchievePassed(vararg ticketAchieve : TicketAchievesRef)
    @Update
    suspend fun setPracticeAchievePassed(vararg practiceAchieve : PracticeAchievesRef)
    @Update
    suspend fun setTicketPassed(ticket : TicketRef)
    @Update
    suspend fun setTicketQuestionPassed(ticket : TicketQuestionRef)

}