package com.codingub.belarusianhistory.network.models.achieves

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Achieve(
   @Json(name = "id") val id : String,
   @Json(name = "name") val name : String,
   @Json(name = "info") val info : String,
   @Json(name = "isPassed") val isPassed: Boolean,
   @Json(name = "type") val type: Int // AchieveType
)
