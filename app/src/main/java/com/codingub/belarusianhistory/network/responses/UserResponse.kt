package com.codingub.belarusianhistory.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(
    @Json(name = "token") val token: String
)