package com.codingub.belarusianhistory.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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

    @Update
    suspend fun setPracticeAchievePassed(vararg practiceAchieve : PracticeAchievesRef)
}