package com.codingub.belarusianhistory.domain.model

import java.io.Serializable

data class Answer(
    val answerId: Int,
    val answerName: String,
    val isTrue: Int,
    val pqId: Int
) : Serializable