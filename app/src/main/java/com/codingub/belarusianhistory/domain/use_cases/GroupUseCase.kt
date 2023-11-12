package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.userdata.Group
import com.codingub.belarusianhistory.data.remote.network.requests.CreateGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.UserLoginGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.UserUidGroupRequest
import com.codingub.belarusianhistory.data.repository.groups.GroupRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(request: CreateGroupRequest): ServerResponse<Unit> {
        return repository.createGroup(request)
    }
}

class DeleteGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(request: DeleteGroupRequest): ServerResponse<Unit> {
        return repository.deleteGroup(request)
    }
}

class InviteUserToGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(request: UserUidGroupRequest): ServerResponse<Unit> {
        return repository.inviteUserToGroup(request)
    }
}

class DeleteUserFromGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(request: UserLoginGroupRequest): ServerResponse<Unit> {
        return repository.deleteUserFromGroup(request)
    }
}

class GetAllGroupsFromTeacherUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(login: String): ServerResponse<List<Group>> {
        return repository.getAllGroupsFromTeacher(login)
    }
}

class GetGroupFromUserUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(login: String): ServerResponse<List<Group>> {
        return repository.getUserGroupInfo(login)
    }
}

