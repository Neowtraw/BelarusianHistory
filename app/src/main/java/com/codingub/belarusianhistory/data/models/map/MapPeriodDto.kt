package com.codingub.belarusianhistory.data.models.map

import com.codingub.belarusianhistory.sdk.MapCategory
import java.util.UUID

data class MapPeriodDto(
    val id: String = UUID.randomUUID().toString(),
    val period: Int, // can be -
    val mapTypeId: String
)