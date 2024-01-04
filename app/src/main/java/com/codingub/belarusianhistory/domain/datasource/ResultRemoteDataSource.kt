package com.codingub.belarusianhistory.domain.datasource

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.userdata.ResultDto

interface ResultRemoteDataSource {

    suspend fun getTypeResults(
        userLogin: String,
        type: AchieveType
    ): ServerResponse<List<ResultDto>>

    suspend fun getAllResults(
        userLogin: String
    ): ServerResponse<List<ResultDto>>

    suspend fun setResults(
        userLogin: String,
        answer: Any?,
        achieve: AchieveDto
    ): ServerResponse<Unit>

    suspend fun resetResults(
        userLogin: String,
        type: AchieveType
    ): ServerResponse<Unit>
}