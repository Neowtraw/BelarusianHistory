package com.codingub.belarusianhistory.sdk.models.userdata

import com.codingub.belarusianhistory.sdk.models.achieves.Results
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class User(
    val id: String,
    val login: String,
    val password: String,
    val username: String,
    val accessLevel: AccessLevel,
    val results: List<Results> = emptyList()
)
