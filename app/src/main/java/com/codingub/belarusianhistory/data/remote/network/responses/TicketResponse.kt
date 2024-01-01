package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.sdk.models.tickets.TicketDto

data class TicketResponse(
    val ticketList: List<TicketDto>
)