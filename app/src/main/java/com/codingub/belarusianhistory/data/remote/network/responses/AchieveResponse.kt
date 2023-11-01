package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve

data class AchieveResponse(
    val achieveList: List<Achieve>
)