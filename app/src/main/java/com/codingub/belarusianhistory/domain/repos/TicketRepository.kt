package com.codingub.belarusianhistory.domain.repos

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.tickets.TicketDto

interface TicketRepository {
    suspend fun getAllTickets(): ServerResponse<List<TicketDto>>

    suspend fun insertTicket(
        name: String,
        timer: Long,
        achieve: AchieveDto? = null
    ): ServerResponse<Unit>

    suspend fun resetTicket(
        name: String
    ): ServerResponse<Unit>
}