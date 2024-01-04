package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.data.models.achieves.AchieveDto

data class AchieveResponse(
    val achieveList: List<AchieveDto>
)