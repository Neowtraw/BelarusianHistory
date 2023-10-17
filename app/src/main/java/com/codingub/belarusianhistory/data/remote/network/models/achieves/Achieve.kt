package com.codingub.belarusianhistory.data.remote.network.models.achieves

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Achieve(
   val id : String,
   val name : String,
   val info : String,
   val isPassed: Boolean,
   val type: Int // AchieveType
)
