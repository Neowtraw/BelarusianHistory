package com.codingub.belarusianhistory.data.remote.network.requests

import com.codingub.belarusianhistory.sdk.AccessLevel


data class RegisterRequest(
    val login: String,
    val username: String,
    val password: String,
    val accessLevel: Int
)

data class LoginRequest(
    val login: String,
    val password: String
)

data class RoleRequest(
    val login: String,
    val accessLevel: Int
)
