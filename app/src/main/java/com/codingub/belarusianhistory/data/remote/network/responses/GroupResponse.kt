package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.data.models.userdata.GroupDto

data class GroupResponse(
    val groups: List<GroupDto>
)