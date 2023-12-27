package com.codingub.belarusianhistory.data.repos

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.models.Event
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

interface EventRepository {
    suspend fun getAllEvents(): ServerResponse<List<Event>>
}

class EventRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : EventRepository {

    override suspend fun getAllEvents(): ServerResponse<List<Event>> {
        return try {
            val data = api.getAllEvents().events
            ServerResponse.OK(data)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}