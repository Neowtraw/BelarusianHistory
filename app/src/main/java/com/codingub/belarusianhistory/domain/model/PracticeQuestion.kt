package com.codingub.belarusianhistory.domain.model

import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.sdk.TaskType
import java.io.Serializable

data class PracticeQuestion(
    val id: Int,
    val taskType: TaskType,
    val name: String,
    val info: String,
    val answers: List<Answer>,
    val achievement: PracticeAchieves?
) : Serializable
