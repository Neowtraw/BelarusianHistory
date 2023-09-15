package com.codingub.belarusianhistory.data.repository

import com.codingub.belarusianhistory.data.local.db.dao.AppDao
import com.codingub.belarusianhistory.domain.model.Achieves.Achieve
import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.domain.repository.AppRepository
import com.codingub.belarusianhistory.sdk.AchievesCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val dao: AppDao
) : AppRepository{

    override suspend fun getAllTicketQuestions(): List<TicketQuestion> {
        return dao.getAllTicketQuestions().map {
            it.toTicketQuestion()
        }
    }

    override suspend fun getAllTicketAchieves(): List<TicketAchieves> {
        return dao.getAllTicketAchieves().map {
            it.toTicketAchieves()
        }
    }

    override suspend fun getAllPracticeAchieves(): List<PracticeAchieves> {
        return dao.getAllPracticeAchieves().map {
            it.toPracticeAchieves()
        }
    }

    override suspend fun getAllTickets(): List<Ticket> {
        return dao.getAllTickets().map {
            it.toTicket()
        }
    }

    override fun getTicketById(id: Int): Flow<Ticket> {
        return dao.getTicketById(id).map {
            it.toTicket()
        }
    }

    override suspend fun getTicketQuestionsById(id: Int): TicketQuestion {
        return dao.getTicketQuestionsById(id).toTicketQuestion()
    }

    override suspend fun getTicketAchievesByPassed(isPassed: Int): List<TicketAchieves> {
        return dao.getTicketAchievesByPassed(isPassed).map {
            it.toTicketAchieves()
        }
    }

    override suspend fun getPracticeAchievesByPassed(isPassed: Int): List<PracticeAchieves> {
        return dao.getPracticeAchievesByPassed(isPassed).map {
            it.toPracticeAchieves()
        }
    }

    /*
        Additional
     */

    override suspend fun getAchieves(achievesCategory: AchievesCategory): List<Achieve> {
        return when (achievesCategory) {
            AchievesCategory.Practice -> dao.getAllPracticeAchieves().map { it.toPracticeAchieves() }
            AchievesCategory.Tickets -> dao.getAllTicketAchieves().map { it.toTicketAchieves() }
        }
    }
}