package com.codingub.belarusianhistory.data.repository.users


import com.codingub.belarusianhistory.data.local.pref.UserConfig
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.utils.logger.HistoryLogger
import retrofit2.HttpException
import javax.inject.Inject

private val log: HistoryLogger = HistoryLogger()

interface UserRepository {

    suspend fun signUp(request: RegisterRequest)
    suspend fun signIn(login: String, password: String)
    suspend fun authenticate()
}

class UserRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : UserRepository {

    override suspend fun signUp(request: RegisterRequest) {
        api.signUp(request)
        signIn(request.login, request.password)
    }

    override suspend fun signIn(
        login: String,
        password: String
    ) {
        val response = api.signIn(
            LoginRequest(
                login, password
            )
        )
        UserConfig.setToken(response.token)
        api.signIn(
            LoginRequest(
                login, password
            )
        )
    }

    override suspend fun authenticate() {
        api.authenticate("Bearer ${UserConfig.getToken()}")
    }
}