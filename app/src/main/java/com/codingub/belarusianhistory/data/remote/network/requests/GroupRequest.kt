package com.codingub.belarusianhistory.data.remote.network.requests

data class UserLoginGroupRequest(
    val login: String,
    val groupId: String
)

data class UserUidGroupRequest(
    val uid: String,
    val groupId: String
)

data class CreateGroupRequest(
    val teacherId: String,
    val groupName: String
)

data class DeleteGroupRequest(
    val teacherId: String,
    val groupId: String
)