package com.codingub.belarusianhistory.domain.repos

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.models.events.EventDto

interface EventRepository {
    suspend fun getAllEvents(): ServerResponse<List<EventDto>>
}