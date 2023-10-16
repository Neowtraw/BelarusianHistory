package com.codingub.belarusianhistory.data.remote.network.models.userdata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Group(
  @Json(name = "id")  val id: String,
  @Json(name = "name")  val name: String,
  @Json(name = "teacherId")  val teacherId: User,
  @Json(name = "students")  val students: List<User> = emptyList()
)
