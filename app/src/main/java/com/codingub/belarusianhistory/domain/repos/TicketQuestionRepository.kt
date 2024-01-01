package com.codingub.belarusianhistory.domain.repos

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.models.achieves.AchieveDto
import com.codingub.belarusianhistory.sdk.models.tickets.TicketQuestionDto

interface TicketQuestionRepository {

    suspend fun getAllTq(): ServerResponse<List<TicketQuestionDto>>
    suspend fun getTqByTicketId(ticketId: String) : ServerResponse<List<TicketQuestionDto>>

    suspend fun insertTq(
        name: String,
        info: String,
        achieve: AchieveDto? = null,

        ticketId: String
    ): ServerResponse<Unit>

    suspend fun deleteTq(
        questionId: String
    ): ServerResponse<Unit>
}
