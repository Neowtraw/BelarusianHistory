package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.sdk.models.userdata.ResultDto

data class ResultResponse(
    val results: List<ResultDto>
)