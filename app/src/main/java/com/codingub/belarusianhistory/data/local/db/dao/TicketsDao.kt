package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.codingub.belarusianhistory.data.local.db.entity.TicketEntity
import com.codingub.belarusianhistory.data.local.db.entity.ticket.TicketRef
import kotlinx.coroutines.flow.Flow


@Dao
interface TicketsDao {

    //Необходимо для "Теория"
    @Transaction
    @Query("SELECT * FROM Ticket")
    suspend fun getAllTickets() : List<TicketEntity>

    //чтение определенного Ticket
    @Transaction
    @Query("SELECT * FROM Ticket WHERE ticketId = :id")
    fun getTicketById(id: Int) : Flow<TicketEntity>

    @Query("UPDATE Ticket SET isPassed = 0")
    suspend fun resetAllTickets()

    @Query("UPDATE 'Ticket' SET isPassed=:passed WHERE ticketId=:id")
    suspend fun updateTicketPassed(id: Int, passed: Int)
}