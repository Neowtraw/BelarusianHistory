package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.models.achieves.Achieve
import com.codingub.belarusianhistory.sdk.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.data.repos.TicketQuestionRepository
import javax.inject.Inject

class GetAllTqUseCase @Inject constructor(private val repository: TicketQuestionRepository) {

    suspend operator fun invoke(): ServerResponse<List<TicketQuestion>> {
        return repository.getAllTq()
    }
}

class GetTqByTicketIdUseCase @Inject constructor(private val repository: TicketQuestionRepository){

    suspend operator fun invoke(ticketId: String): ServerResponse<List<TicketQuestion>> {
        return repository.getTqByTicketId(ticketId)
    }
}

class InsertTqUseCase @Inject constructor(private val repository: TicketQuestionRepository) {

    suspend operator fun invoke(
        name: String,
        info: String,
        achieve: Achieve?,
        ticketId: String
    ): ServerResponse<Unit> {
        return repository.insertTq(name, info, achieve, ticketId)
    }
}

class ResetTqUseCase @Inject constructor(private val repository: TicketQuestionRepository) {

    suspend operator fun invoke(questionId: String): ServerResponse<Unit> {
        return repository.deleteTq(questionId)
    }
}