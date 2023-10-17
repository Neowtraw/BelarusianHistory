package com.codingub.belarusianhistory.data.repository.users


import android.util.Log
import com.codingub.belarusianhistory.data.local.pref.UserConfig
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.remote.network.responses.TokenResponse
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
        Log.d("", "signup1")
        api.signUp(request)
        Log.d("", "signup2")
        signIn(request.login, request.password)
        Log.d("", "signup3")

    }

    override suspend fun signIn(
        login: String,
        password: String
    ) {
        Log.d("","hellow1")

        val response = api.signIn(
            LoginRequest(
                login, password
            )
        )
        Log.d("","hellow2")
        UserConfig.setToken(response.token)
        Log.d("","hellow3")
    }

    override suspend fun authenticate() {
        Log.d("","authenticate1")
        api.authenticate("Bearer ${UserConfig.getToken()}")
        Log.d("","authenticate2")
    }
}