package com.codingub.belarusianhistory.data.remote.network.models

import com.codingub.belarusianhistory.data.remote.network.models.practices.Answer

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
