package com.codingub.belarusianhistory.data.repository.tickets

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.models.tickets.Ticket
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTicketRequest
import javax.inject.Inject

interface TicketRepository {
//    suspend fun getAllTickets(): List<Ticket>
//
//    suspend fun insertTicket(
//        name: String,
//        timer: Long,
//        achieve: Achieve? = null
//    ): String
//
//    suspend fun resetTicket(
//        name: String
//    ): String
}

class TicketRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : TicketRepository {

//    override suspend fun getAllTickets(): List<Ticket> {
//        return api.getAllTickets().data
//    }
//
//    override suspend fun insertTicket(name: String, timer: Long, achieve: Achieve?): String {
//        return api.insertTicket(
//            InsertTicketRequest(
//                name = name,
//                timer = timer,
//                achieve = achieve
//            )
//        ).message
//    }
//
//    override suspend fun resetTicket(name: String): String {
//        return api.resetTicket(
//            DeleteTicketRequest(name)
//        ).message
//    }
}