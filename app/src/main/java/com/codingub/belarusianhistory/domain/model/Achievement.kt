package com.codingub.belarusianhistory.domain.model

data class Achievement(
    val id: Int,
    val name: String,
    val info: String,
    var isPassed: Boolean
)