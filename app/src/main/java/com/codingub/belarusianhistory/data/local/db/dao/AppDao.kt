package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.codingub.belarusianhistory.data.local.db.entity.PracticeQuestionEntity
import com.codingub.belarusianhistory.data.local.db.entity.TicketEntity
import com.codingub.belarusianhistory.data.local.db.entity.TicketQuestionEntity
import com.codingub.belarusianhistory.data.local.db.entity.achieves.PracticeAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.achieves.TicketAchievesRef
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao{

    @Transaction
    @Query("SELECT * FROM PracticeQuestion")
    suspend fun getAllPracticeQuestions() : Flow<List<PracticeQuestionEntity>>

    @Transaction
    @Query("SELECT * FROM TicketQuestion")
    suspend fun getAllTicketQuestions() : Flow<List<TicketQuestionEntity>>

    @Transaction
    @Query("SELECT * FROM TicketAchieves")
    suspend fun getAllTicketAchieves() : Flow<List<TicketAchievesRef>>

    @Transaction
    @Query("SELECT * FROM PracticeAchieves")
    suspend fun getAllPracticeAchieves() : Flow<List<PracticeAchievesRef>>

    @Transaction
    @Query("SELECT * FROM Ticket")
    suspend fun getAllTickets() : Flow<List<TicketEntity>>

    @Transaction
    @Query("SELECT * FROM Ticket WHERE ticketId")
    suspend fun getPracticeQuestionsByTicketQuestion()
}