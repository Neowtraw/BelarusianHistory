package com.codingub.belarusianhistory.data.local.db.entity.questions

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingub.belarusianhistory.data.local.db.entity.PracticeQuestionEntity
import com.codingub.belarusianhistory.data.local.db.entity.achieves.PracticeAchievesRef
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.sdk.TaskType

@Entity(tableName = "PracticeQuestion")
data class PracticeQuestionRef(
    @PrimaryKey(autoGenerate = false)
    val pqId: Int,
    val taskType: TaskType, //Int
    val pqName: String,
    val pqInfo: String,
    val answer: String,
    val tqId: Int //много к одному c TicketQuestion
)