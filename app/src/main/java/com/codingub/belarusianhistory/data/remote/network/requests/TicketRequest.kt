package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.sdk.models.achieves.AchieveDto

data class InsertTicketRequest(
    val name: String,
    val timer : Long,
    val achieve: AchieveDto? = null
)

data class DeleteTicketRequest(
    val name: String
)