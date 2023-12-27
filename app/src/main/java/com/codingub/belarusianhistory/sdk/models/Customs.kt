package com.codingub.belarusianhistory.sdk.models

import com.codingub.belarusianhistory.sdk.models.practices.Answer

data class CustomTicketQuestion(
    val id : String,
    val name: String,
    val info: String,

    var practices: List<CustomPracticeQuestion> = emptyList()
)

data class CustomPracticeQuestion(
    val name : String,
    val taskType: Int, //TaskType
    val answers: List<Answer> = emptyList()
)
