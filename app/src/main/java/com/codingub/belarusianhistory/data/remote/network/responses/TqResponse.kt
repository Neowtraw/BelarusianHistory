package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion

data class TqResponse(
    val tqList: List<TicketQuestion>
)
