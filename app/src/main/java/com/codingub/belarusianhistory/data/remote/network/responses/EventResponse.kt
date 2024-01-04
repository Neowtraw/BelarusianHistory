package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.data.models.events.EventDto

data class EventResponse(
   val events: List<EventDto>
)