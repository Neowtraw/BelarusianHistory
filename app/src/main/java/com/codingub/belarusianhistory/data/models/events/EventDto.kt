package com.codingub.belarusianhistory.data.models.events

import com.codingub.belarusianhistory.sdk.EventType
import com.google.gson.annotations.SerializedName

data class EventDto(
   @SerializedName("id") val id: String,
   @SerializedName("title") val title: String,
   @SerializedName("description") val description: String,
   @SerializedName("type") val type: EventType
)
