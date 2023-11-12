package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.repository.users.UserRepository
import com.codingub.belarusianhistory.sdk.AccessLevel
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val repository: UserRepository){

    suspend operator fun invoke(parameters: LoginRequest): ServerResponse<Unit> {
        return repository.signIn(login = parameters.login, password = parameters.password)
    }
}

class RegisterUseCase @Inject constructor(private val repository: UserRepository)  {

    suspend operator fun invoke(parameters: RegisterRequest): ServerResponse<Unit> {
        return repository.signUp(request = parameters)
    }

}

class AuthUseCase @Inject constructor(private val repository: UserRepository){

    suspend operator fun invoke(): ServerResponse<Unit> {
        return repository.authenticate()
    }
}

class RoleUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(parameters: AccessLevel) : ServerResponse<Unit>{
        return repository.changeRoleByLogin(
            accessLevel = parameters
        )
    }
}
