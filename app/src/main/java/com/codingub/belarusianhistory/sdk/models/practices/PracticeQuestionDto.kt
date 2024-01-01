package com.codingub.belarusianhistory.sdk.models.practices

import com.codingub.belarusianhistory.sdk.TaskType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PracticeQuestion(
    @SerializedName("id") val id: String,
    @SerializedName("taskType") val taskType: TaskType,
    @SerializedName("name") val name: String,
    @SerializedName("info") val info: String,

    val answers: List<Answer> = emptyList()
) : Serializable

data class Answer(
    @SerializedName("info")  val info: String,
    @SerializedName("isTrue") val isTrue: Boolean
) : Serializable
