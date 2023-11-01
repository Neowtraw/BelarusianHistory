package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.models.tickets.Ticket
import com.codingub.belarusianhistory.data.repository.tickets.TicketRepository
import javax.inject.Inject

class GetAllTicketsUseCase @Inject constructor(private val repository: TicketRepository) {

    suspend operator fun invoke(): ServerResponse<List<Ticket>> {
        return repository.getAllTickets()
    }
}

class InsertTicketUseCase @Inject constructor(private val repository: TicketRepository) {

    suspend operator fun invoke(
        name: String,
        timer: Long,
        achieve: Achieve?
    ): ServerResponse<Unit> {
        return repository.insertTicket(name, timer, achieve)
    }
}

class ResetTicketUseCase @Inject constructor(private val repository: TicketRepository) {

    suspend operator fun invoke(name: String): ServerResponse<Unit> {
        return repository.resetTicket(name)
    }
}