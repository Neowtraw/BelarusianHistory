package com.codingub.belarusianhistory.data.repository.tickets

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTqRequest
import javax.inject.Inject

interface TicketQuestionRepository {

    suspend fun getAllTq(
        ticketId: String
    ) : List<TicketQuestion>

    suspend fun insertTq(
        name: String,
        info: String,
        achieve: Achieve? = null,

        ticketId: String
    ) : String

    suspend fun deleteTq(
        ticketId: String,
        questionId: String
    ) : String
}

class TicketQuestionRepositoryImpl @Inject constructor(
   private val api : HistoryAppApi
) : TicketQuestionRepository{

    override suspend fun getAllTq(ticketId: String): List<TicketQuestion> {
        return api.getAllTq(ticketId = ticketId).data
    }

    override suspend fun insertTq(
        name: String,
        info: String,
        achieve: Achieve?,
        ticketId: String
    ): String {
        return api.insertTq(InsertTqRequest(
            name = name,
            info = info,
            achieve = achieve,
            ticketId = ticketId
        )).message
    }

    override suspend fun deleteTq(ticketId: String, questionId: String): String {
        return api.deleteTq(DeleteTqRequest(
            ticketId = ticketId,
            questionId = questionId
        )).message
    }
}