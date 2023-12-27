package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.repos.EventRepository
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {

    suspend operator fun invoke() = repository.getAllEvents()
}