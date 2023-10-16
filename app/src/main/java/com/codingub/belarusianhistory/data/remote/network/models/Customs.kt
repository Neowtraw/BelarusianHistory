package com.codingub.belarusianhistory.data.remote.network.models

import com.codingub.belarusianhistory.data.remote.network.models.practices.Answer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomTicketQuestion(
    @Json(name = "id") val id : String,
    @Json(name = "name") val name: String,
    @Json(name = "info") val info: String,

    @Json(name = "practices") var practices: List<CustomPracticeQuestion> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CustomPracticeQuestion(
    @Json(name = "name") val name : String,
    @Json(name = "taskType") val taskType: Int, //TaskType
    @Json(name = "answers") val answers: List<Answer> = emptyList()
)
