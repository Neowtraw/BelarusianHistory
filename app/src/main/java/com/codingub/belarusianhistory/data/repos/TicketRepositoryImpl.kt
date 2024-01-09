package com.codingub.belarusianhistory.data.repos

import android.util.Log
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetTicketsRequest
import com.codingub.belarusianhistory.domain.repos.TicketRepository
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject


class TicketRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : TicketRepository {

    override suspend fun getAllTickets(): ServerResponse<List<TicketDto>> {
        return try {
            val result = api.getAllTickets()
            ServerResponse.OK(result.ticketList)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun insertTicket(
        name: String,
        timer: Long,
        achieve: AchieveDto?
    ): ServerResponse<Unit> {
        return try {
            api.insertTicket(
                InsertTicketRequest(
                    name = name,
                    timer = timer,
                    achieve = achieve
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

    override suspend fun resetTicket(name: String): ServerResponse<Unit> {
        return try {
            api.resetTicket(
                ResetTicketRequest(name)
            )
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteTicketsByIds(ids: List<String>): ServerResponse<Unit> {
        return try {
            Log.d("delete", "succ")
            api.resetTicketsByIds(ResetTicketsRequest(ids))
            Log.d("delete", "succ2")
            ServerResponse.OK()
        } catch (e: HttpException) {
            e.printStackTrace()
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}