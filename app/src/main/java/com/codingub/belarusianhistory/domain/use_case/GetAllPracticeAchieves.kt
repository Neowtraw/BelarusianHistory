package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPracticeAchieves @Inject constructor(
    private val repository: AppRepository
) {

    operator fun invoke() : Flow<List<PracticeAchieves>> {
        return repository.getAllPracticeAchieves()
    }

}