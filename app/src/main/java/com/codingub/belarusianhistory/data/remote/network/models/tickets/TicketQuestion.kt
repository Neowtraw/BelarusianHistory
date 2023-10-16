package com.codingub.belarusianhistory.data.remote.network.models.tickets


import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.models.practices.PracticeQuestion
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TicketQuestion(
    @Json(name = "id") val id : String,
    @Json(name = "name") val name: String,
    @Json(name = "info") val info: String,

    @Json(name = "practices") val practices: List<PracticeQuestion> = emptyList(),
    @Json(name = "achieve") val achieve: Achieve? = null
)