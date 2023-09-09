package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.repository.AppRepository
import javax.inject.Inject

class GetPracticeAchievesByPassed @Inject constructor(
    private val repository: AppRepository) {

    suspend operator fun invoke(isPassed: Int) : List<PracticeAchieves>{
        return repository.getPracticeAchievesByPassed(isPassed)
    }
}