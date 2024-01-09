package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.data.models.practices.Answer

data class InsertPqRequest(
    val taskType: Int,
    val name: String,
    val info: String,
    val answers: List<Answer>,
    )

data class ResetPqRequest (
    val questionId: String
)

data class ResetPqsRequest (
    val ids: List<String>
)