package com.codingub.belarusianhistory.sdk.models

import com.codingub.belarusianhistory.sdk.EventType
import com.google.gson.annotations.SerializedName

data class Event(
   @SerializedName("id") val id: String,
   @SerializedName("title") val title: String,
   @SerializedName("description") val description: String,
   @SerializedName("type") val type: EventType
)
