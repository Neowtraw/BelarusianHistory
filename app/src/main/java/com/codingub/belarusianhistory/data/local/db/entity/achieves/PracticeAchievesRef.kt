package com.codingub.belarusianhistory.data.local.db.entity.achieves

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves

@Entity(tableName = "PracticeAchieves")
data class PracticeAchievesRef(
    @PrimaryKey(autoGenerate = false)
    val achievementId: Int,
    val achievementName: String,
    val achievementInfo: String,
    var isPassed: Int,
    val pqId: Int // один к одному с PracticeQuestion
) {
    fun toPracticeAchieves() : PracticeAchieves {
        return PracticeAchieves(
            id = achievementId,
            name = achievementName,
            info = achievementInfo,
            isPassed = isPassed
        )
    }
}