package com.codingub.belarusianhistory.sdk.models.practices

import com.codingub.belarusianhistory.sdk.TaskType
import java.io.Serializable

data class PracticeQuestion(
   val id: String,
   val taskType: TaskType,
   val name: String,
   val info: String,

   val answers: List<Answer> = emptyList()
) : Serializable

data class Answer(
    val info: String,
    val isTrue: Boolean
) : Serializable
