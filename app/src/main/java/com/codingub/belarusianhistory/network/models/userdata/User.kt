package com.codingub.belarusianhistory.network.models.userdata

import com.codingub.belarusianhistory.network.models.achieves.Results
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: String,
    @Json(name = "login") val login: String,
    @Json(name = "password") val password: String,
    @Json(name = "username") val username: String,
    @Json(name = "UId") val UId: String, //for groups
    @Json(name = "accessLevel") val accessLevel: Int, //permissions
    @Json(name = "photo") val photo: String = "",
    @Json(name = "results")  val results: List<Results> = emptyList()
)
