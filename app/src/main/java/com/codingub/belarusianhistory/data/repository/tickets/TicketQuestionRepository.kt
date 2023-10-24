package com.codingub.belarusianhistory.data.repository.tickets

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTqRequest
import retrofit2.HttpException
import javax.inject.Inject

interface TicketQuestionRepository {

    suspend fun getAllTq(
        ticketId: String
    ): ServerResponse<List<TicketQuestion>>

    suspend fun insertTq(
        name: String,
        info: String,
        achieve: Achieve? = null,

        ticketId: String
    ): ServerResponse<Unit>

    suspend fun deleteTq(
        ticketId: String,
        questionId: String
    ): ServerResponse<Unit>
}

class TicketQuestionRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : TicketQuestionRepository {

    override suspend fun getAllTq(ticketId: String): ServerResponse<List<TicketQuestion>> {
        return try {
            val result = api.getAllTq(ticketId = ticketId)
            ServerResponse.OK(result)
        } catch (e: HttpException) {
            if (e.code() == 400) {
                ServerResponse.BadRequest(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else if (e.code() == 409) {
                ServerResponse.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else {
                ServerResponse.UnknownError()
            }
        } catch (e: Exception) {
            ServerResponse.UnknownError()
        }
    }

    override suspend fun insertTq(
        name: String,
        info: String,
        achieve: Achieve?,
        ticketId: String
    ): ServerResponse<Unit> {
        return try {
            api.insertTq(
                InsertTqRequest(
                    name = name,
                    info = info,
                    achieve = achieve,
                    ticketId = ticketId
                )
            )
            ServerResponse.OK()
        } catch (e: HttpException) {
            if (e.code() == 400) {
                ServerResponse.BadRequest(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else if (e.code() == 409) {
                ServerResponse.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else {
                ServerResponse.UnknownError()
            }
        } catch (e: Exception) {
            ServerResponse.UnknownError()
        }
    }

    override suspend fun deleteTq(ticketId: String, questionId: String): ServerResponse<Unit> {
        return try {
            api.deleteTq(
                DeleteTqRequest(
                    ticketId = ticketId,
                    questionId = questionId
                )
            )
            ServerResponse.OK()
        } catch (e: HttpException) {
            if (e.code() == 400) {
                ServerResponse.BadRequest(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else if (e.code() == 409) {
                ServerResponse.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else {
                ServerResponse.UnknownError()
            }
        } catch (e: Exception) {
            ServerResponse.UnknownError()
        }
    }
}