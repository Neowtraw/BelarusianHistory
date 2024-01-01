package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.sdk.models.achieves.AchieveDto
import com.google.gson.annotations.SerializedName

data class AddResultRequest(
    val login: String,
    val answer: Any?,
    val achieve: AchieveDto
)

data class GetAllResultsRequest(
    val login: String
)

/** Reset all results for user with this type **/
data class ResetResultRequest(
    val userLogin: String,
    val type: AchieveType
)
