package com.codingub.belarusianhistory.data.models.map

import com.codingub.belarusianhistory.sdk.MapCategory

data class MapTypeDto(
    val id: String,
    val title: String,
    val description: String,
    val type: MapCategory,
    val periods: List<MapPeriodDto>
)