package com.codingub.belarusianhistory.sdk.models.achieves

import com.codingub.belarusianhistory.sdk.AchieveType
import com.google.gson.annotations.SerializedName

data class AchieveDto(
   @SerializedName("id") val id : String,
   @SerializedName("name") val name : String,
   @SerializedName("info") val info : String,
   @SerializedName("type") val type: AchieveType
)
