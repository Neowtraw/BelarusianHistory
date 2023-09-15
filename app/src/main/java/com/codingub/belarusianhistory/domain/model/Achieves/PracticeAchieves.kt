package com.codingub.belarusianhistory.domain.model.Achieves

data class PracticeAchieves(
   override val id: Int,
   override val name: String,
   override val info: String,
   override var isPassed: Int,
    val pqId: Int
) : Achieve{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PracticeAchieves) return false
        return true
    }
}
