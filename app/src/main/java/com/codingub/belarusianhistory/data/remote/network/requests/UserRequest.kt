package com.codingub.belarusianhistory.data.remote.network.requests


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