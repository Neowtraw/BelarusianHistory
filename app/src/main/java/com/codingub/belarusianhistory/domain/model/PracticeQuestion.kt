package com.codingub.belarusianhistory.domain.model

data class PracticeQuestion(
    val id: Int,
    val taskType: Int,
    val name: String,
    val info: String,
    val answer: String
)
