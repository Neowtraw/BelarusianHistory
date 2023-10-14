package com.codingub.belarusianhistory.data.repository.users
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.network.AuthResult
import com.codingub.belarusianhistory.network.requests.LoginRequest
import com.codingub.belarusianhistory.network.requests.RegisterRequest
import com.codingub.belarusianhistory.utils.logger.HistoryLogger
import retrofit2.HttpException
import javax.inject.Inject

private val log : HistoryLogger = HistoryLogger()

interface UserRepository {

    suspend fun signUp(request: RegisterRequest): AuthResult<Unit>
    suspend fun signIn(login: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}

class UserRepositoryImpl @Inject constructor(
    private val api : HistoryAppApi
) : UserRepository{

    override suspend fun signUp(request: RegisterRequest): AuthResult<Unit> {
        return try {

            api.signUp(request)
            signIn(request.login, request.password)
        } catch(e: HttpException) {

            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            log.d("",e.message.toString())
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(login: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = LoginRequest(
                    login = login,
                    password = password
                )
            )
//            prefs.edit()
//                .putString("jwt", response.token)
//                .apply()
            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
//            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
//            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}