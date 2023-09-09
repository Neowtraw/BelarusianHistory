package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getAllTicketAchieves @Inject constructor(
    private val repository: AppRepository
) {

    operator fun invoke() : Flow<List<TicketAchieves>> {
        return repository.getAllTicketAchieves()
    }
}