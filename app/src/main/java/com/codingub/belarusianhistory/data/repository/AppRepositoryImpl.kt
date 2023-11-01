package com.codingub.belarusianhistory.data.repository

import com.codingub.belarusianhistory.data.local.db.dao.PracticeAchievesDao
import com.codingub.belarusianhistory.data.local.db.dao.PracticeQuestionDao
import com.codingub.belarusianhistory.data.local.db.dao.TicketAchievesDao
import com.codingub.belarusianhistory.data.local.db.dao.TicketQuestionDao
import com.codingub.belarusianhistory.data.local.db.dao.TicketsDao
import com.codingub.belarusianhistory.data.local.db.entity.questions.PracticeQuestionRef
import com.codingub.belarusianhistory.domain.model.Achieves.Achieve
import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.domain.repository.AppRepository
import com.codingub.belarusianhistory.sdk.AchievesCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val practiceAchievesDao: PracticeAchievesDao,
    private val ticketAchievesDao: TicketAchievesDao,
) : AppRepository{

    override suspend fun getAllTicketAchieves(): List<TicketAchieves> {
        return ticketAchievesDao.getAllTicketAchieves().map {
            it.toTicketAchieves()
        }
    }

    override suspend fun getAllPracticeAchieves(): List<PracticeAchieves> {
        return practiceAchievesDao.getAllPracticeAchieves().map {
            it.toPracticeAchieves()
        }
    }

    override suspend fun getTicketAchievesByPassed(isPassed: Int): List<TicketAchieves> {
        return ticketAchievesDao.getTicketAchievesByPassed(isPassed).map {
            it.toTicketAchieves()
        }
    }

    override suspend fun getPracticeAchievesByPassed(isPassed: Int): List<PracticeAchieves> {
        return practiceAchievesDao.getPracticeAchievesByPassed(isPassed).map {
            it.toPracticeAchieves()
        }
    }

    /*
        Additional
     */

    override suspend fun getAchieves(achievesCategory: AchievesCategory): List<Achieve> {
        return when (achievesCategory) {
            AchievesCategory.Practice -> practiceAchievesDao.getAllPracticeAchieves().map { it.toPracticeAchieves() }
            AchievesCategory.Tickets -> ticketAchievesDao.getAllTicketAchieves().map { it.toTicketAchieves() }
        }
    }
}