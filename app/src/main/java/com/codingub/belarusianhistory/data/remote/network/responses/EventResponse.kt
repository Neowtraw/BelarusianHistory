package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.sdk.models.Event
import com.google.gson.annotations.SerializedName

data class EventResponse(
   val events: List<Event>
)