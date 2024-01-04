package com.codingub.belarusianhistory.data.models.userdata

import com.codingub.belarusianhistory.sdk.AchieveType
import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : AchieveType,
    @SerializedName("answer") val answer: String,
    @SerializedName("achieveId") val achieveId: String, //id of object (Ticket/Practice)
    @SerializedName("userLogin") val userLogin: String
)