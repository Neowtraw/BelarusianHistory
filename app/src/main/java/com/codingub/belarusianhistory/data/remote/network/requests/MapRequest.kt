package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.data.models.map.MapLabelDto
import com.codingub.belarusianhistory.data.models.map.MapTypeDto

data class LabelRequest(
    val label: MapLabelDto
)
data class AddPeriodRequest(
    val mapType: MapTypeDto,
    val map: String,
    val period: Int
)