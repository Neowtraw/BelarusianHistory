package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.repos.TicketRepository
import javax.inject.Inject

class GetAllTicketsUseCase @Inject constructor(private val repository: TicketRepository) {

    suspend operator fun invoke(): ServerResponse<List<TicketDto>> {
        return repository.getAllTickets()
    }
}

class InsertTicketUseCase @Inject constructor(private val repository: TicketRepository) {

    suspend operator fun invoke(
        name: String,
        timer: Long,
        achieve: AchieveDto?
    ): ServerResponse<Unit> {
        return repository.insertTicket(name, timer, achieve)
    }
}

class ResetTicketUseCase @Inject constructor(private val repository: TicketRepository) {

    suspend operator fun invoke(name: String): ServerResponse<Unit> {
        return repository.resetTicket(name)
    }
}

class DeleteTicketsByIdsUseCase @Inject constructor(private val repository: TicketRepository) {
    suspend operator fun invoke(ids: List<String>) = repository.deleteTicketsByIds(ids)
}