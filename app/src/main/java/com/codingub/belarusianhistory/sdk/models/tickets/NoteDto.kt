package com.codingub.belarusianhistory.sdk.models.tickets

import com.google.gson.annotations.SerializedName


data class NoteDto(
    @SerializedName("id")  val id : String,
    @SerializedName("info") val info: String,
    @SerializedName("ticketId") val ticketId: String
)
