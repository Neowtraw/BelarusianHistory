package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.local.pref.UserConfig
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.userdata.Group
import com.codingub.belarusianhistory.domain.use_cases.CreateGroupUseCase
import com.codingub.belarusianhistory.domain.use_cases.DeleteGroupUseCase
import com.codingub.belarusianhistory.domain.use_cases.DeleteUserFromGroupUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetAllGroupsUseCase
import com.codingub.belarusianhistory.domain.use_cases.InviteUserToGroupUseCase
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val createGroupUseCase: CreateGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val inviteUserToGroupUseCase: InviteUserToGroupUseCase,
    private val deleteUserFromGroupUseCase: DeleteUserFromGroupUseCase
) : ViewModel() {

    //for showing current groups with custom shimmer
    private val getGroupChannel = Channel<ServerResponse<List<Group>>>()
    val groupState = getGroupChannel.receiveAsFlow()

    private val createGroupChannel = Channel<ServerResponse<Unit>>()
    val createGroupState = createGroupChannel.receiveAsFlow()

    private val deleteGroupChannel = Channel<ServerResponse<Unit>>()
    val deleteGroupState = deleteGroupChannel.receiveAsFlow()

    private val inviteUserGroupChannel = Channel<ServerResponse<Unit>>()
    val inviteUserGroupState = inviteUserGroupChannel.receiveAsFlow()

    private val deleteUserGroupChannel = Channel<ServerResponse<Unit>>()
    val deleteUserGroupState = deleteUserGroupChannel.receiveAsFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getGroups()
        }
    }

    private suspend fun getGroups() {
        getGroupChannel.send(ServerResponse.Loading(true))
        val result = getAllGroupsUseCase(UserConfig.getLogin())
        getGroupChannel.send(result)
    }

    suspend fun createGroup(name: String) {
        createGroupChannel.send(ServerResponse.Loading(true))
        when (UserConfig.getAccessLevel()) {
            AccessLevel.Admin, AccessLevel.Teacher -> {
                val result = createGroupUseCase(UserConfig.getLogin(), name)
                createGroupChannel.send(result)
            }

            AccessLevel.User -> {
                createGroupChannel.send(ServerResponse.Error(Resource.string(R.string.access_error)))
            }
        }

    }

    suspend fun deleteGroup(groupId: String) {
        deleteGroupChannel.send(ServerResponse.Loading(true))
        when (UserConfig.getAccessLevel()) {
            AccessLevel.Admin, AccessLevel.Teacher -> {
                val result = deleteGroupUseCase(UserConfig.getLogin(), groupId)
                deleteGroupChannel.send(result)
            }

            AccessLevel.User -> {
                deleteGroupChannel.send(ServerResponse.Error(Resource.string(R.string.access_error)))
            }
        }
    }

    suspend fun inviteUserToGroup(groupId: String) {
        inviteUserGroupChannel.send(ServerResponse.Loading(true))
        when (UserConfig.getAccessLevel()) {
            AccessLevel.Admin, AccessLevel.Teacher -> {
                val result = inviteUserToGroupUseCase(UserConfig.getLogin(), groupId)
                inviteUserGroupChannel.send(result)
            }

            AccessLevel.User -> {
                inviteUserGroupChannel.send(ServerResponse.Error(Resource.string(R.string.access_error)))
            }
        }
    }

    suspend fun deleteUserFromGroup(groupId: String) {
        deleteUserGroupChannel.send(ServerResponse.Loading(true))
        when (UserConfig.getAccessLevel()) {
            AccessLevel.Admin, AccessLevel.Teacher -> {
                val result = deleteUserFromGroupUseCase(UserConfig.getLogin(), groupId)
                deleteUserGroupChannel.send(result)
            }

            AccessLevel.User -> {
                deleteUserGroupChannel.send(ServerResponse.Error(Resource.string(R.string.access_error)))
            }
        }
    }


}