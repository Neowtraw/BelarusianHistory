package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.domain.repository.AppRepository
import javax.inject.Inject

class GetTicketQuestionsById @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(id: Int) : TicketQuestion {
        return repository.getTicketQuestionsById(id)
    }

}