package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto

data class TqResponse(
    val tqList: List<TicketQuestionDto>
)
