package com.codingub.belarusianhistory.data.remote.network.models.practices

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class PracticeQuestion(
   val id: String,
   val taskType: Int,
   val name: String,
   val info: String,

   val answers: List<Answer> = emptyList()
)

data class Answer(
    val info: String,
    val isTrue: Boolean
)
