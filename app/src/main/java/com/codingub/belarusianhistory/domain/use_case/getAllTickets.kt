package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.repository.AppRepository
import javax.inject.Inject

class getAllTickets @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke() : List<Ticket>{
        return repository.getAllTickets()
    }
}