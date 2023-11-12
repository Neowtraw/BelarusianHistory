package com.codingub.belarusianhistory.data.remote.network.models.userdata


data class Group(
  val id: String,
  val name: String,
  val teacher: User,
  val userIds: List<User> = emptyList()
)
