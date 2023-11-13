package com.codingub.belarusianhistory.data.repository.groups

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.userdata.Group
import com.codingub.belarusianhistory.data.remote.network.requests.CreateGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.GroupRequest
import retrofit2.HttpException
import javax.inject.Inject

interface GroupRepository {

    suspend fun getAllGroups(login: String): ServerResponse<List<Group>>
    suspend fun createGroup(login: String, groupName: String): ServerResponse<Unit>
    suspend fun deleteGroup(login: String, groupId: String): ServerResponse<Unit>
    suspend fun inviteUserToGroup(login: String, groupId: String): ServerResponse<Unit>
    suspend fun deleteUserFromGroup(login: String, groupId: String): ServerResponse<Unit>
}

class GroupRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : GroupRepository {

    override suspend fun getAllGroups(login: String): ServerResponse<List<Group>> {
        return try {
            val result = api.getAllGroups(login)
            ServerResponse.OK(result.groups)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun createGroup(login: String, groupName: String): ServerResponse<Unit> {
        return try {

            api.createGroup(CreateGroupRequest(login, groupName))
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteGroup(login: String, groupId: String): ServerResponse<Unit> {
        return try {
            api.deleteGroup(GroupRequest(login, groupId))
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun inviteUserToGroup(login: String, groupId: String): ServerResponse<Unit> {
        return try {
            api.inviteUserToGroup(GroupRequest(login, groupId))
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteUserFromGroup(login: String, groupId: String): ServerResponse<Unit> {
        return try {
            api.deleteUserFromGroup(GroupRequest(login, groupId))
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}