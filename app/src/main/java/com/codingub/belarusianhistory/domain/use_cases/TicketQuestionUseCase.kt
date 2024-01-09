package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.repos.TicketQuestionRepository
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.domain.repos.TicketRepository
import javax.inject.Inject

class GetAllTqUseCase @Inject constructor(private val repository: TicketQuestionRepository) {

    suspend operator fun invoke(): ServerResponse<List<TicketQuestionDto>> {
        return repository.getAllTq()
    }
}

class GetTqByTicketIdUseCase @Inject constructor(private val repository: TicketQuestionRepository) {

    suspend operator fun invoke(ticketId: String): ServerResponse<List<TicketQuestionDto>> {
        return repository.getTqByTicketId(ticketId)
    }
}

class InsertTqUseCase @Inject constructor(private val repository: TicketQuestionRepository) {

    suspend operator fun invoke(
        name: String,
        info: String,
        achieve: AchieveDto?,
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

class DeleteTqsByIdsUseCase @Inject constructor(private val repository: TicketQuestionRepository) {
    suspend operator fun invoke(ids: List<String>) = repository.deletePqsByIds(ids)
}