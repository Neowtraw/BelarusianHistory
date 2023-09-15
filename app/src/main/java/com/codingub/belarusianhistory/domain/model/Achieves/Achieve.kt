package com.codingub.belarusianhistory.domain.model.Achieves

interface Achieve {
    val id: Int
    val name: String
    val info: String
    var isPassed: Int

    override fun equals(other: Any?): Boolean
}