package com.codingub.belarusianhistory.domain.model

import com.codingub.belarusianhistory.sdk.TaskType

data class PracticeQuestion(
    val id: Int,
    val taskType: TaskType,
    val name: String,
    val info: String,
    val answer: String,
    val achievement: PracticeAchieves
)
