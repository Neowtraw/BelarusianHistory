package com.codingub.belarusianhistory.domain.repos

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.models.userdata.GroupDto

interface GroupRepository {

    suspend fun getAllGroups(login: String): ServerResponse<List<GroupDto>>
    suspend fun createGroup(login: String, groupName: String): ServerResponse<Unit>
    suspend fun deleteGroup(login: String, groupId: String): ServerResponse<Unit>
    suspend fun inviteUserToGroup(login: String, groupId: String): ServerResponse<Unit>
    suspend fun deleteUserFromGroup(login: String, groupId: String): ServerResponse<Unit>
}
