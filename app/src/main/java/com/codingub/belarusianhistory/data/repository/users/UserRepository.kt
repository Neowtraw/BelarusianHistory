package com.codingub.belarusianhistory.data.repository.users


import com.codingub.belarusianhistory.data.local.pref.UserConfig
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.ui.auth.AuthResult
import com.codingub.belarusianhistory.utils.logger.HistoryLogger
import retrofit2.HttpException
import javax.inject.Inject

private val log: HistoryLogger = HistoryLogger()

interface UserRepository {

    suspend fun signUp(request: RegisterRequest): AuthResult<Unit>
    suspend fun signIn(login: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}

class UserRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : UserRepository {

    override suspend fun signUp(request: RegisterRequest): AuthResult<Unit> {
        return try {
            api.signUp(request)
            signIn(request.login, request.password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else if (e.code() == 409) {
                AuthResult.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else {
                AuthResult.UnknownError()
            }
        }
    }

    override suspend fun signIn(
        login: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                LoginRequest(
                    login = login,
                    password = password
                )
            )
            UserConfig.setToken(response.token)
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else if (e.code() == 409) {
                AuthResult.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else {
                AuthResult.UnknownError()
            }
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            api.authenticate("Bearer ${UserConfig.getToken()}")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else if (e.code() == 409) {
                AuthResult.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else {
                AuthResult.UnknownError()
            }
        }
    }
}