package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.sdk.models.tickets.Ticket

data class TicketResponse(
    val ticketList: List<Ticket>
)