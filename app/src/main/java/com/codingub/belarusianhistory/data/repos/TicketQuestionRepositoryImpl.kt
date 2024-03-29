package com.codingub.belarusianhistory.data.repos

import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetTqsRequest
import com.codingub.belarusianhistory.domain.repos.TicketQuestionRepository
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class TicketQuestionRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : TicketQuestionRepository {

    override suspend fun getAllTq()
            : ServerResponse<List<TicketQuestionDto>> {
        return try {
            val result = api.getAllTq()
            ServerResponse.OK(result.tqList)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getTqByTicketId(ticketId: String): ServerResponse<List<TicketQuestionDto>> {
        return try {
            val result = api.getTqByTicketId(ticketId = ticketId)
            ServerResponse.OK(result.tqList)
        } catch (e: HttpException) {
            e.printStackTrace()
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun insertTq(
        name: String,
        info: String,
        achieve: AchieveDto?,
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
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteTq(questionId: String): ServerResponse<Unit> {
        return try {
            api.resetTq(
                ResetTqRequest(
                    questionId = questionId
                )
            )
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deletePqsByIds(ids: List<String>): ServerResponse<Unit> {
        return try {
            api.resetTqsByIds(ResetTqsRequest(ids))
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}