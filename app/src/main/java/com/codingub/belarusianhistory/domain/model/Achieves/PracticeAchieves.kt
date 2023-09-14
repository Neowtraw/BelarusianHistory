package com.codingub.belarusianhistory.domain.model.Achieves

data class PracticeAchieves(
    val id: Int,
    override val name: String,
    override val info: String,
    override var isPassed: Int,
    val pqId: Int
) : Achieve
