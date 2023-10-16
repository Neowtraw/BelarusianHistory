package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.domain.SuspendUseCase
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.repository.users.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val repository: UserRepository)
    : SuspendUseCase<LoginRequest, Unit>(Dispatchers.IO){

    override suspend fun execute(parameters: LoginRequest) {
        repository.signIn(login = parameters.login, password = parameters.password)
    }
}

class RegisterUseCase @Inject constructor(private val repository: UserRepository)
    : SuspendUseCase<RegisterRequest, Unit>(Dispatchers.IO){

    override suspend fun execute(parameters: RegisterRequest) {
        repository.signUp(request = parameters)
    }

}

class AuthUseCase @Inject constructor(private val repository: UserRepository)
    : SuspendUseCase<Unit, Unit>(Dispatchers.IO){

    override suspend fun execute(parameters: Unit) {
       repository.authenticate()
    }
}