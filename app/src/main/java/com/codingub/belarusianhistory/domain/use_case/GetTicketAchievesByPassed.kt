package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.repository.AppRepository
import javax.inject.Inject

class GetTicketAchievesByPassed  @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(isPassed: Int) : List<TicketAchieves>{
        return repository.getTicketAchievesByPassed(isPassed)
    }

}