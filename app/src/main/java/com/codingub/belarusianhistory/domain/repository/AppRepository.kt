package com.codingub.belarusianhistory.domain.repository

import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    //необходимо для "Практика"
    suspend fun getAllTicketQuestions() : List<TicketQuestion>

    //Получение отдельно теоретических достижений
    suspend fun getAllTicketAchieves() : Flow<List<TicketAchieves>>

    //Получение отдельно практических достижений
    suspend fun getAllPracticeAchieves() : Flow<List<PracticeAchieves>>

    //Необходимо для "Теория"
    suspend fun getAllTickets() : List<Ticket>

    //чтение определенного Ticket
    suspend fun getTicketById(id: Int) : Flow<Ticket>

    //прохождение практики определенного TicketQuestion
    suspend fun getTicketQuestionsById(id: Int) : TicketQuestion

}