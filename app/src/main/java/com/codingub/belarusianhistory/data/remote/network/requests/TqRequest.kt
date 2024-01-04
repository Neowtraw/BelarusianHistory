package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.data.models.achieves.AchieveDto

data class DeleteTqRequest (
    val questionId: String
)
data class InsertTqRequest(
    val name: String,
    val info: String,
    val achieve: AchieveDto? = null,

    val ticketId: String
)