package com.codingub.belarusianhistory.data.repository.groups

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.userdata.Group
import com.codingub.belarusianhistory.data.remote.network.requests.CreateGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.UserLoginGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.UserUidGroupRequest
import retrofit2.HttpException
import javax.inject.Inject

interface GroupRepository {

    suspend fun getAllGroupsFromTeacher(teacherId: String): ServerResponse<List<Group>>
    suspend fun createGroup(request: CreateGroupRequest): ServerResponse<Unit>
    suspend fun deleteGroup(request: DeleteGroupRequest): ServerResponse<Unit>
    suspend fun inviteUserToGroup(request: UserUidGroupRequest): ServerResponse<Unit>
    suspend fun deleteUserFromGroup(request: UserLoginGroupRequest): ServerResponse<Unit>
    suspend fun getUserGroupInfo(userId: String): ServerResponse<List<Group>>
}

class GroupRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : GroupRepository {

    override suspend fun getAllGroupsFromTeacher(login: String): ServerResponse<List<Group>> {
        return try {
            val result = api.getAllGroupsFromTeacher(login)
            ServerResponse.OK(result.groups)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun createGroup(request: CreateGroupRequest): ServerResponse<Unit> {
        return try {

            val result = api.createGroup(request)
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteGroup(request: DeleteGroupRequest): ServerResponse<Unit> {
        return try {
            val result = api.deleteGroup(request)
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun inviteUserToGroup(request: UserUidGroupRequest): ServerResponse<Unit> {
        return try {
            val result = api.inviteUserToGroup(request)
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteUserFromGroup(request: UserLoginGroupRequest): ServerResponse<Unit> {
        return try {
            val result = api.deleteUserFromGroup(request)
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getUserGroupInfo(login: String): ServerResponse<List<Group>> {
        return try {
            val result = api.getGroupFromUser(login)
            ServerResponse.OK(result.groups)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}