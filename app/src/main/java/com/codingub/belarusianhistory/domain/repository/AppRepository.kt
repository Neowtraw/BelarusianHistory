package com.codingub.belarusianhistory.domain.repository

import com.codingub.belarusianhistory.domain.model.Achieves.Achieve
import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.sdk.AchievesCategory
import com.codingub.belarusianhistory.sdk.Language
import com.codingub.belarusianhistory.sdk.ThemeType
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    //необходимо для "Практика"
    suspend fun getAllTicketQuestions() : List<TicketQuestion>

    //Получение отдельно теоретических достижений
    suspend fun getAllTicketAchieves() : List<TicketAchieves>

    //Получение отдельно практических достижений
    suspend fun getAllPracticeAchieves() : List<PracticeAchieves>

    //Необходимо для "Теория"
    suspend fun getAllTickets() : List<Ticket>

    //чтение определенного Ticket
    fun getTicketById(id: Int) : Flow<Ticket>

    //прохождение практики определенного TicketQuestion
    suspend fun getTicketQuestionsById(id: Int) : TicketQuestion

    //получение количества пройденной практики и билетов
    suspend fun getTicketAchievesByPassed(isPassed: Int) : List<TicketAchieves>
    suspend fun getPracticeAchievesByPassed(isPassed: Int) : List<PracticeAchieves>

    /*
        Additional
     */

    //Получение определенных достижений
    suspend fun getAchieves(achievesCategory: AchievesCategory) : List<Achieve>


}