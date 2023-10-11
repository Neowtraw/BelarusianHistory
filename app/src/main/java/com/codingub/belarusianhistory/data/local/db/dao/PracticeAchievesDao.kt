package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.codingub.belarusianhistory.data.local.db.entity.achieves.PracticeAchievesRef

@Dao
interface PracticeAchievesDao {

    //Получение отдельно практических достижений
    @Transaction
    @Query("SELECT * FROM PracticeAchieves")
    suspend fun getAllPracticeAchieves() : List<PracticeAchievesRef>

    //получение количества пройденной практики
    @Transaction
    @Query("SELECT * FROM PracticeAchieves WHERE isPassed = :isPassed")
    suspend fun getPracticeAchievesByPassed(isPassed: Int) : List<PracticeAchievesRef>

    @Query("UPDATE 'PracticeAchieves' SET isPassed=:passed WHERE achievementId=:id")
    suspend fun updatePracticeAchievesPassed(id: Int, passed: Int)

    @Query("UPDATE 'PracticeAchieves' SET isPassed = 0")
    suspend fun resetAllPracticeAchieves()
}