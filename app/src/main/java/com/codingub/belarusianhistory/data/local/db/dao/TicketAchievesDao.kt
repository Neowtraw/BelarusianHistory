package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.codingub.belarusianhistory.data.local.db.entity.achieves.TicketAchievesRef

@Dao
interface TicketAchievesDao {

    //Получение отдельно теоретических достижений
    @Transaction
    @Query("SELECT * FROM TicketAchieves")
    suspend fun getAllTicketAchieves() : List<TicketAchievesRef>

    //получение количества пройденных билетов
    @Transaction
    @Query("SELECT * FROM TicketAchieves WHERE isPassed = :isPassed")
    suspend fun getTicketAchievesByPassed(isPassed: Int) : List<TicketAchievesRef>

    @Update
    suspend fun setTicketAchievePassed(vararg ticketAchieve : TicketAchievesRef)

}