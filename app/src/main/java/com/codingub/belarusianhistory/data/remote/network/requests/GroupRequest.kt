package com.codingub.belarusianhistory.data.remote.network.requests

data class GroupRequest(
    val login: String,
    val groupId: String
)


data class CreateGroupRequest(
    val teacher: String,
    val groupName: String
)
