package com.codingub.belarusianhistory.sdk.models.userdata

import com.codingub.belarusianhistory.sdk.AccessLevel
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: String, // Primary Key
    @SerializedName("login") val login: String, // Unique key
    @SerializedName("password") val password: String,
    @SerializedName("username") val username: String,
    @SerializedName("accessLevel") val accessLevel: AccessLevel
)
