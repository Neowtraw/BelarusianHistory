package com.codingub.belarusianhistory.sdk.models.userdata

import com.google.gson.annotations.SerializedName


data class GroupDto(
  @SerializedName("id") val id: String,
  @SerializedName("name") val name: String,
  @SerializedName("teacher") val teacher: String,
  @SerializedName("users") val users: List<String> = emptyList()
)
