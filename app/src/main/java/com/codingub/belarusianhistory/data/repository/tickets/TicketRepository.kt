package com.codingub.belarusianhistory.data.repository.tickets

import android.util.Log
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.models.tickets.Ticket
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTicketRequest
import retrofit2.HttpException
import javax.inject.Inject

interface TicketRepository {
    suspend fun getAllTickets(): ServerResponse<List<Ticket>>

    suspend fun insertTicket(
        name: String,
        timer: Long,
        achieve: Achieve? = null
    ): ServerResponse<Unit>

    suspend fun resetTicket(
        name: String
    ): ServerResponse<Unit>
}

class TicketRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : TicketRepository {

    override suspend fun getAllTickets(): ServerResponse<List<Ticket>> {
        return try {
            val result = api.getAllTickets()
            Log.d("err", "ITSS ALLL GOOOOD")
            ServerResponse.OK(result.ticketList)
        } catch (e: HttpException) {
            if (e.code() == 400) {
                ServerResponse.BadRequest(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else if(e.code() == 404) {
                ServerResponse.NotFound()
            } else if (e.code() == 409) {
                ServerResponse.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else {
                Log.d("err", e.message().toString())
                ServerResponse.UnknownError()
            }
        } catch (e: Exception) {
            Log.d("err", e.message.toString())
            ServerResponse.UnknownError()
        }
    }

    override suspend fun insertTicket(
        name: String,
        timer: Long,
        achieve: Achieve?
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
            if (e.code() == 400) {
                ServerResponse.BadRequest(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else if(e.code() == 404) {
                ServerResponse.NotFound()
            } else if (e.code() == 409) {
                ServerResponse.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else {
                ServerResponse.UnknownError()
            }
        } catch (e: Exception) {
            ServerResponse.UnknownError()
        }
    }

    override suspend fun resetTicket(name: String): ServerResponse<Unit> {
        return try {
            api.resetTicket(
                DeleteTicketRequest(name)
            )
            ServerResponse.OK()
        } catch (e: HttpException) {
            if (e.code() == 400) {
                ServerResponse.BadRequest(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else if(e.code() == 404) {
                ServerResponse.NotFound()
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