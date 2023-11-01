package com.codingub.belarusianhistory.data.remote.network.models.userdata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Group(
  val id: String,
  val name: String,
  val teacherId: String,
  val students: List<User> = emptyList()
)
