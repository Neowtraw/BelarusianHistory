package com.codingub.belarusianhistory.data.repos

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.models.achieves.Achieve
import com.codingub.belarusianhistory.sdk.models.tickets.Ticket
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTicketRequest
import retrofit2.HttpException
import java.util.concurrent.CancellationException
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
            ServerResponse.OK(result.ticketList)
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            if(e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
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
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            if(e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun resetTicket(name: String): ServerResponse<Unit> {
        return try {
            api.resetTicket(
                DeleteTicketRequest(name)
            )
            ServerResponse.OK()
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            if(e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}