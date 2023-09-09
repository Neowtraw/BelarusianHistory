package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketById @Inject constructor(
    private val repository: AppRepository
) {

    operator fun invoke(id: Int) : Flow<Ticket> {
        return repository.getTicketById(id)
    }

}