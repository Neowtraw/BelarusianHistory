package com.codingub.belarusianhistory.domain.repos

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.sdk.models.achieves.AchieveDto

interface AchieveRepository {

    suspend fun getAllAchieves() : ServerResponse<List<AchieveDto>>
    suspend fun getTypeAchieves(type: AchieveType) : ServerResponse<List<AchieveDto>>
}