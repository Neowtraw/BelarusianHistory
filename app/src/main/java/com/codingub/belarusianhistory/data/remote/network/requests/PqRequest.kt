package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.sdk.models.practices.Answer

data class InsertPqRequest(
    val taskType: Int,
    val name: String,
    val info: String,
    val answers: List<Answer>,
    )

data class DeletePqRequest (
    val questionId: String
)