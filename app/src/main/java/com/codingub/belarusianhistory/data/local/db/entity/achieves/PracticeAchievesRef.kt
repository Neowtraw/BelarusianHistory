package com.codingub.belarusianhistory.data.local.db.entity.achieves

import androidx.room.Entity
import com.codingub.belarusianhistory.domain.model.PracticeAchieves

@Entity(tableName = "PracticeAchieves")
data class PracticeAchievesRef(
    val achievementId: Int,
    val achievementName: String,
    val achievementInfo: String,
    var isPassed: Boolean,
    val pqId: Long // один к одному с PracticeQuestion
) {
    fun toPracticeAchieves() : PracticeAchieves {
        return PracticeAchieves(
            id = achievementId,
            name = achievementName,
            info = achievementInfo,
            isPassed = isPassed,
            pqId = pqId
        )
    }
}