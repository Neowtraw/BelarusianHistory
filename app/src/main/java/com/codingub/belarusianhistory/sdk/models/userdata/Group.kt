package com.codingub.belarusianhistory.sdk.models.userdata


data class Group(
  val id: String,
  val name: String,
  val teacher: String,
  val users: List<String> = emptyList()
)
