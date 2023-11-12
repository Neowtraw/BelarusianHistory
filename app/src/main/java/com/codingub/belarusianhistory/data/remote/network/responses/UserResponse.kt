package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.sdk.AccessLevel

data class TokenResponse(
    val token: String,
    val username: String,
    val accessLevel: AccessLevel,
    val UID: String
)

