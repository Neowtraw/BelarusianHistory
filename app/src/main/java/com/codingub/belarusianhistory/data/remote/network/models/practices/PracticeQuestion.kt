package com.codingub.belarusianhistory.data.remote.network.models.practices

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PracticeQuestion(
   @Json(name = "id") val id: String,
   @Json(name = "taskType") val taskType: Int,
   @Json(name = "name") val name: String,
   @Json(name = "info") val info: String,

   @Json(name = "answers") val answers: List<Answer> = emptyList()
)

@JsonClass(generateAdapter = true)
data class Answer(
    @Json(name = "info") val info: String,
    @Json(name = "isTrue") val isTrue: Boolean
)
