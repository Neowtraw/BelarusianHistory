package com.codingub.belarusianhistory.ui.statistic

import android.provider.ContactsContract.Groups
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.local.pref.UserConfig
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.data.remote.network.models.userdata.Group
import com.codingub.belarusianhistory.domain.use_cases.CreateGroupUseCase
import com.codingub.belarusianhistory.domain.use_cases.DeleteGroupUseCase
import com.codingub.belarusianhistory.domain.use_cases.DeleteUserFromGroupUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetAllGroupsFromTeacherUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetGroupFromUserUseCase
import com.codingub.belarusianhistory.domain.use_cases.InviteUserToGroupUseCase
import com.codingub.belarusianhistory.sdk.AccessLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
    private val getAllGroupsFromTeacher: GetAllGroupsFromTeacherUseCase,
    private val getGroupFromUser: GetGroupFromUserUseCase,
    private val createGroup: CreateGroupUseCase,
    private val deleteGroup: DeleteGroupUseCase,
    private val inviteUserToGroup: InviteUserToGroupUseCase,
    private val deleteUserFromGroup: DeleteUserFromGroupUseCase
) : ViewModel() {

    //for showing current groups with custom shimmer
    private val getGroupChannel = Channel<ServerResponse<List<Group>>>()
    val groupState = getGroupChannel.receiveAsFlow()

    private val createGroupChannel = Channel<ServerResponse<List<Group>>>()
    val createGroupState = createGroupChannel.receiveAsFlow()

    private val deleteGroupChannel = Channel<ServerResponse<List<Group>>>()
    val deleteGroupState = deleteGroupChannel.receiveAsFlow()

    private val inviteUserToGroupChannel = Channel<ServerResponse<List<Group>>>()
    val inviteUserGroupState = inviteUserToGroupChannel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            //getGroups()
        }
    }

    suspend fun getGroups() {
        getGroupChannel.send(ServerResponse.Loading(true))
        when (UserConfig.getAccessLevel()) {
            AccessLevel.User -> {
               val result = getGroupFromUser(UserConfig.getLogin())
                getGroupChannel.send(result)
            }

            AccessLevel.Admin, AccessLevel.Teacher -> {
                val result = getAllGroupsFromTeacher(UserConfig.getLogin())
                getGroupChannel.send(result)

            }
        }
    }

    suspend fun createGroup(name: String){


    }

    suspend fun deleteGroup() {

    }

    suspend fun inviteUserToGroup(){

    }

    suspend fun deleteUserFromGroup(){

    }


}