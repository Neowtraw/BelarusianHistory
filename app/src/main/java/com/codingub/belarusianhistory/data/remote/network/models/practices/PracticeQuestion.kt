package com.codingub.belarusianhistory.data.remote.network.models.practices

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
