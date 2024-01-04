package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.data.models.userdata.ResultDto

data class ResultResponse(
    val results: List<ResultDto>
)