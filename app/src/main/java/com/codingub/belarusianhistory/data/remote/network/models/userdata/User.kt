package com.codingub.belarusianhistory.data.remote.network.models.userdata

import com.codingub.belarusianhistory.data.remote.network.models.achieves.Results
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class User(
    val id: String,
    val login: String,
    val password: String,
    val username: String,
    val UId: String, //for groups
    val accessLevel: Int, //permissions
    val results: List<Results> = emptyList()
)
