package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.data.models.achieves.AchieveDto

data class InsertTicketRequest(
    val name: String,
    val timer : Long,
    val achieve: AchieveDto? = null
)

data class ResetTicketRequest(
    val name: String
)

data class ResetTicketsRequest(
    val ids: List<String>
)