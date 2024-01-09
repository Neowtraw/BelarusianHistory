package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.data.models.achieves.AchieveDto

data class ResetTqRequest (
    val questionId: String
)

data class ResetTqsRequest(
    val ids: List<String>
)

data class InsertTqRequest(
    val name: String,
    val info: String,
    val achieve: AchieveDto? = null,

    val ticketId: String
)