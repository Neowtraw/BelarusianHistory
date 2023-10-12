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
    private val ticketsDao: TicketsDao,
    private val practiceQuestionDao: PracticeQuestionDao,
    private val practiceAchievesDao: PracticeAchievesDao,
    private val ticketQuestionDao: TicketQuestionDao,
    private val ticketAchievesDao: TicketAchievesDao,
) : AppRepository{

    override suspend fun getAllTicketQuestions(): List<TicketQuestion> {
        return ticketQuestionDao.getAllTicketQuestions().map {
            it.toTicketQuestion()
        }
    }

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

    override suspend fun getAllTickets(): List<Ticket> {
        return ticketsDao.getAllTickets().map {
            it.toTicket()
        }
    }

    override fun getTicketById(id: Int): Flow<Ticket> {
        return ticketsDao.getTicketById(id).map {
            it.toTicket()
        }
    }

    override suspend fun getTicketQuestionsById(id: Int): TicketQuestion {
        return ticketQuestionDao.getTicketQuestionsById(id).toTicketQuestion()
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
        Insert/Delete
     */

    override suspend fun resetAllPracticeAchieves() {
        practiceAchievesDao.resetAllPracticeAchieves()
    }

    override suspend fun resetAllTicketAchieves() {
        ticketAchievesDao.resetAllTicketAchieves()
    }

    override suspend fun resetAllTickets() {
        ticketsDao.resetAllTickets()
    }

    override suspend fun deletePracticeQuestionById(questionId: Int) {
        practiceQuestionDao.deletePracticeQuestionById(questionId)
    }

    override suspend fun insertPracticeQuestion(question: PracticeQuestion) {
        practiceQuestionDao.insertPracticeQuestion(PracticeQuestionRef(
            pqId = question.id,
            taskType = question.taskType,
            pqName = question.name,
            pqInfo = question.info,
            tqId = question.tqId
        ))
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