package com.codingub.belarusianhistory.data.models.userdata

import com.google.gson.annotations.SerializedName


data class GroupDto(
  @SerializedName("id") val id: String,
  @SerializedName("name") val name: String,
  @SerializedName("teacher") val teacher: String,
  @SerializedName("users") val users: List<String> = emptyList()
)
