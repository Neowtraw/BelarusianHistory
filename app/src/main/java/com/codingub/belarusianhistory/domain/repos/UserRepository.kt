package com.codingub.belarusianhistory.domain.repos

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.sdk.models.achieves.AchieveDto
import com.codingub.belarusianhistory.sdk.models.userdata.ResultDto

interface UserRepository {

    /*
        Authentication
     */

    suspend fun signUp(
        login: String,
        username: String,
        password: String,
        accessLevel: AccessLevel
    ): ServerResponse<Unit>

    suspend fun signIn(login: String, password: String): ServerResponse<Unit>
    suspend fun authenticate(): ServerResponse<Unit>

    /*
        Users
     */

    suspend fun changeRoleByLogin(accessLevel: AccessLevel): ServerResponse<Unit>

    /*
        Results
     */
    suspend fun getTypeResults(
        userLogin: String,
        type: AchieveType
    ): ServerResponse<List<ResultDto>>

    suspend fun getAllResults(
        userLogin: String
    ): ServerResponse<List<ResultDto>>

    suspend fun setResult(
        userLogin: String,
        answer: Any?,
        achieve: AchieveDto
    ): ServerResponse<Unit>

    suspend fun resetResults(
        userLogin: String,
        type: AchieveType
    ): ServerResponse<Unit>
}
