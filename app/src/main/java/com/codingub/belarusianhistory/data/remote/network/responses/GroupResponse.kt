package com.codingub.belarusianhistory.data.remote.network.responses

import com.codingub.belarusianhistory.sdk.models.userdata.Group
import com.google.gson.annotations.SerializedName

data class GroupResponse(
    val groups: List<Group>
)