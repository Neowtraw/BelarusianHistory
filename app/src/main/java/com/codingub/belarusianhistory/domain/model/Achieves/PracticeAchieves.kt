package com.codingub.belarusianhistory.domain.model.Achieves

import java.io.Serializable

data class PracticeAchieves(
   override val id: Int,
   override val name: String,
   override val info: String,
   override var isPassed: Int,
    val pqId: Int
) : Achieve, Serializable{

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is PracticeAchieves) return false
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = id
//        result = 31 * result + name.hashCode()
//        result = 31 * result + info.hashCode()
//        result = 31 * result + isPassed
//        result = 31 * result + pqId
//        return result
//    }
}
