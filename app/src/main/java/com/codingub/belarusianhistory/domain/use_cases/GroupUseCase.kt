package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.models.userdata.Group
import com.codingub.belarusianhistory.data.repos.GroupRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(teacher : String, groupName : String): ServerResponse<Unit> {
        return repository.createGroup(teacher, groupName)
    }
}

class DeleteGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(teacherId: String, groupId: String): ServerResponse<Unit> {
        return repository.deleteGroup(teacherId, groupId)
    }
}

class InviteUserToGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(uid:String, groupId: String): ServerResponse<Unit> {
        return repository.inviteUserToGroup(uid, groupId)
    }
}

class DeleteUserFromGroupUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(login: String, groupId: String): ServerResponse<Unit> {
        return repository.deleteUserFromGroup(login, groupId)
    }
}

class GetAllGroupsUseCase @Inject constructor(private val repository: GroupRepository) {

    suspend operator fun invoke(login: String): ServerResponse<List<Group>> {
        return repository.getAllGroups(login)
    }
}



