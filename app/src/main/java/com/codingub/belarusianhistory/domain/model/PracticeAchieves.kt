package com.codingub.belarusianhistory.domain.model

data class PracticeAchieves(
    val id: Int,
    val name: String,
    val info: String,
    var isPassed: Boolean,
    val pqId: Long
)
