package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion

data class PqResponse(
    val pqList: List<PracticeQuestion>
)