package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.domain.repository.AppRepository
import javax.inject.Inject

class GetAllTicketQuestions @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke() : List<TicketQuestion>{
        return repository.getAllTicketQuestions()
    }
}