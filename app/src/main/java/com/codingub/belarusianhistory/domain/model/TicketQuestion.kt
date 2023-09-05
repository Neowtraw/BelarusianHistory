package com.codingub.belarusianhistory.domain.model

data class TicketQuestion(
    val id: Int,
    val name: String,
    val info: String,
    var isPassed: Boolean,
    val practiceList: List<PracticeQuestion>
)
