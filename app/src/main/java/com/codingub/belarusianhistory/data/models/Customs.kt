package com.codingub.belarusianhistory.data.models

import com.codingub.belarusianhistory.data.models.practices.Answer

data class CustomTicketQuestionDto(
    val id : String,
    val name: String,
    val info: String,

    var practices: List<CustomPracticeQuestionDto> = emptyList()
)

data class CustomPracticeQuestionDto(
    val name : String,
    val taskType: Int, //TaskType
    val answers: List<Answer> = emptyList()
)
