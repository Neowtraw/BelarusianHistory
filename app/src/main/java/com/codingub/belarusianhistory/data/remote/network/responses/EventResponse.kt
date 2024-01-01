package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.sdk.models.events.EventDto

data class EventResponse(
   val events: List<EventDto>
)