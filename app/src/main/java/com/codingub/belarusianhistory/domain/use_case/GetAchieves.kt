package com.codingub.belarusianhistory.domain.use_case

import com.codingub.belarusianhistory.domain.model.Achieves.Achieve
import com.codingub.belarusianhistory.domain.repository.AppRepository
import com.codingub.belarusianhistory.sdk.AchievesCategory
import javax.inject.Inject

class GetAchieves @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(category: AchievesCategory) : List<Achieve>{
        return repository.getAchieves(category)
    }
}