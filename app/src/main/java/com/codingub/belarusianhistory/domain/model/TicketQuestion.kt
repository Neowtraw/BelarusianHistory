package com.codingub.belarusianhistory.domain.model

data class TicketQuestion(
    val id: Int,
    val name: String,
    val info: String,
    var isPassed: Int,
    val practiceList: List<PracticeQuestion>?
)
