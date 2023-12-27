package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.repos.UserRepository
import com.codingub.belarusianhistory.sdk.AccessLevel
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(login: String, password: String): ServerResponse<Unit> {
        return repository.signIn(login, password)
    }
}

class RegisterUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(
        login: String,
        username: String,
        password: String,
        accessLevel: AccessLevel
    ): ServerResponse<Unit> {
        return repository.signUp(login, username, password, accessLevel)
    }

}

class AuthUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(): ServerResponse<Unit> {
        return repository.authenticate()
    }
}

class RoleUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(parameters: AccessLevel): ServerResponse<Unit> {
        return repository.changeRoleByLogin(
            accessLevel = parameters
        )
    }
}
