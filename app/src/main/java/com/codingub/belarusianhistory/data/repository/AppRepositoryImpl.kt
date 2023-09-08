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
        TODO("Not yet implemented")
    }

    override suspend fun getAllTicketAchieves(): Flow<List<TicketAchieves>> {
        return dao.getAllTicketAchieves().map {
            it.map{ list ->
                list.toTicketAchieves()
            }
        }
    }

    override suspend fun getAllPracticeAchieves(): Flow<List<PracticeAchieves>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTickets(): List<Ticket> {
        TODO("Not yet implemented")
    }

    override suspend fun getTicketById(id: Int): Flow<Ticket> {
        TODO("Not yet implemented")
    }

    override suspend fun getTicketQuestionsById(id: Int): TicketQuestion {
        TODO("Not yet implemented")
    }
}