package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.domain.SuspendUseCase
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.repository.users.UserRepository
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.ui.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val repository: UserRepository) :
    SuspendUseCase<LoginRequest, AuthResult<Unit>>(Dispatchers.IO) {

    override suspend fun execute(parameters: LoginRequest): AuthResult<Unit> {
        return repository.signIn(login = parameters.login, password = parameters.password)
    }
}

class RegisterUseCase @Inject constructor(private val repository: UserRepository) :
    SuspendUseCase<RegisterRequest, AuthResult<Unit>>(Dispatchers.IO) {

    override suspend fun execute(parameters: RegisterRequest): AuthResult<Unit> {
        return repository.signUp(request = parameters)
    }

}

class AuthUseCase @Inject constructor(private val repository: UserRepository) :
    SuspendUseCase<Unit, AuthResult<Unit>>(Dispatchers.IO) {
    override suspend fun execute(parameters: Unit): AuthResult<Unit> {
        return repository.authenticate()
    }
}

class RoleUseCase @Inject constructor(private val repository: UserRepository) :
    SuspendUseCase<AccessLevel, String>(Dispatchers.IO) {

    override suspend fun execute(parameters: AccessLevel) : String{

        Timber.d("roleUserCase")
        return repository.changeRoleByLogin(
            accessLevel = parameters
        )
    }
}
