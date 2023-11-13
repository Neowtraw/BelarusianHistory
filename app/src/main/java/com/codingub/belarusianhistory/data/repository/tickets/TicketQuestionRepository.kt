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

    suspend fun getAllTq(): ServerResponse<List<TicketQuestion>>
    suspend fun getTqByTicketId(ticketId: String) : ServerResponse<List<TicketQuestion>>

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

    override suspend fun getAllTq()
    : ServerResponse<List<TicketQuestion>> {
        return try {
            val result = api.getAllTq()
            ServerResponse.OK(result.tqList)
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getTqByTicketId(ticketId: String): ServerResponse<List<TicketQuestion>> {
        return try {
            val result = api.getTqByTicketId(ticketId = ticketId)
            ServerResponse.OK(result.tqList)
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            ServerResponse.Error(e.message ?: "Unknown error")
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
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            ServerResponse.Error(e.message ?: "Unknown error")
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
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}