package com.codingub.belarusianhistory.data.repository

import com.codingub.belarusianhistory.data.local.db.dao.AppDao
import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.domain.repository.AppRepository
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

    override fun getAllTicketAchieves(): Flow<List<TicketAchieves>> {
        return dao.getAllTicketAchieves().map {list ->
            list.map{
                it.toTicketAchieves()
            }
        }
    }

    override fun getAllPracticeAchieves(): Flow<List<PracticeAchieves>> {
        return dao.getAllPracticeAchieves().map { list ->
            list.map {
                it.toPracticeAchieves()
            }
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
}