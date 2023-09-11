package com.codingub.belarusianhistory.domain.model.Achieves

data class PracticeAchieves(
    val id: Int,
    val name: String,
    val info: String,
    var isPassed: Int,
    val pqId: Int
)
