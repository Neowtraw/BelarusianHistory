package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.sdk.models.achieves.Achieve

data class DeleteTqRequest (
    val questionId: String
)
data class InsertTqRequest(
    val name: String,
    val info: String,
    val achieve: Achieve? = null,

    val ticketId: String
)