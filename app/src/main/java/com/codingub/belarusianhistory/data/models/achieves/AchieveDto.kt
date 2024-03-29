package com.codingub.belarusianhistory.data.models.achieves

import com.codingub.belarusianhistory.sdk.AchieveType
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class AchieveDto(
    @SerializedName("id") val id : String = UUID.randomUUID().toString(),
    @SerializedName("name") var name : String,
    @SerializedName("info") var info : String,
    @SerializedName("type") val type: AchieveType,
    @SerializedName("ownerId") val ownerId: String
)
