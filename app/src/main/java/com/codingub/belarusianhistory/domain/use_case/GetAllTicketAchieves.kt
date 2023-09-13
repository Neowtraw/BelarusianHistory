package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.repository.AppRepository
import javax.inject.Inject

class GetAllTicketAchieves  @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke() : List<TicketAchieves>{
        return repository.getAllTicketAchieves()
    }
}