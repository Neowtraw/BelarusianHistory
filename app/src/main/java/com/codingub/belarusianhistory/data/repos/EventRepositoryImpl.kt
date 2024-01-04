package com.codingub.belarusianhistory.data.repos

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.repos.EventRepository
import com.codingub.belarusianhistory.data.models.events.EventDto
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : EventRepository {

    override suspend fun getAllEvents(): ServerResponse<List<EventDto>> {
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