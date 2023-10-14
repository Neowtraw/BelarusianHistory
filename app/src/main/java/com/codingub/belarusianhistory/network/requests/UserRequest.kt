package com.codingub.belarusianhistory.network.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    @Json(name = "login") val login: String,
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String,
    @Json(name = "accessLevel") val accessLevel: Int
)

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "login") val login: String,
    @Json(name = "password") val password: String
)