package com.codingub.belarusianhistory.domain.repository

import com.codingub.belarusianhistory.domain.model.Achieves.Achieve
import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.sdk.AchievesCategory
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    //Получение отдельно теоретических достижений
    suspend fun getAllTicketAchieves() : List<TicketAchieves>

    //Получение отдельно практических достижений
    suspend fun getAllPracticeAchieves() : List<PracticeAchieves>

    //получение количества пройденной практики и билетов
    suspend fun getTicketAchievesByPassed(isPassed: Int) : List<TicketAchieves>
    suspend fun getPracticeAchievesByPassed(isPassed: Int) : List<PracticeAchieves>

    //Получение определенных достижений
    suspend fun getAchieves(achievesCategory: AchievesCategory) : List<Achieve>



}