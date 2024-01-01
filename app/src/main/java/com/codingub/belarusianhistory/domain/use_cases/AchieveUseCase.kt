package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.domain.repos.AchieveRepository
import com.codingub.belarusianhistory.sdk.AchieveType
import javax.inject.Inject

class GetAllAchievesUseCase @Inject constructor(
    private val repository: AchieveRepository
) {
    suspend operator fun invoke() = repository.getAllAchieves()
}

class GetTypeAchievesUseCase @Inject constructor(
    private val repository: AchieveRepository
) {
    suspend operator fun invoke(type: AchieveType) = repository.getTypeAchieves(type)
}