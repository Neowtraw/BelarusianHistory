package com.codingub.belarusianhistory.data.repos

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.models.userdata.GroupDto
import com.codingub.belarusianhistory.data.remote.network.requests.CreateGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.GroupRequest
import com.codingub.belarusianhistory.domain.repos.GroupRepository
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : GroupRepository {

    override suspend fun getAllGroups(login: String): ServerResponse<List<GroupDto>> {
        return try {
            val result = api.getAllGroups(login)
            ServerResponse.OK(result.groups)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if(e is CancellationException) throw e
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
            if(e is CancellationException) throw e
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
            if(e is CancellationException) throw e
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
            if(e is CancellationException) throw e
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
            if(e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}