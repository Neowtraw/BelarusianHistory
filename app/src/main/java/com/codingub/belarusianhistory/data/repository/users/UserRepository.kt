package com.codingub.belarusianhistory.data.repository.users


import com.codingub.belarusianhistory.data.local.pref.UserConfig
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RoleRequest
import com.codingub.belarusianhistory.sdk.AccessLevel
import retrofit2.HttpException
import javax.inject.Inject


interface UserRepository {

    /*
        Authentication
     */

    suspend fun signUp(
        login: String,
        username: String,
        password: String,
        accessLevel: AccessLevel
    ): ServerResponse<Unit>

    suspend fun signIn(login: String, password: String): ServerResponse<Unit>
    suspend fun authenticate(): ServerResponse<Unit>

    /*
        Users
     */

    suspend fun changeRoleByLogin(accessLevel: AccessLevel): ServerResponse<Unit>
}

class UserRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : UserRepository {

    override suspend fun signUp(
        login: String,
        username: String,
        password: String,
        accessLevel: AccessLevel
    ): ServerResponse<Unit> {
        return try {
            api.signUp(
                RegisterRequest(
                    login = login,
                    username = username,
                    password = password,
                    accessLevel = accessLevel
                )
            )
            signIn(login = login, password = password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                ServerResponse.Unauthorized()
            } else {
                ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun signIn(
        login: String,
        password: String
    ): ServerResponse<Unit> {
        return try {
            val response = api.signIn(
                LoginRequest(
                    login = login,
                    password = password
                )
            )
            UserConfig.apply {
                setToken(response.token)
                setLogin(login)
                setUsername(response.username)
                setAccessLevel(response.accessLevel)
            }
            ServerResponse.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                ServerResponse.Unauthorized()
            } else {
                ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun authenticate(): ServerResponse<Unit> {
        return try {
            api.authenticate("Bearer ${UserConfig.getToken()}")
            ServerResponse.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                ServerResponse.Unauthorized()
            } else {
                ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    /*
        Users
     */
    override suspend fun changeRoleByLogin(
        accessLevel: AccessLevel
    ): ServerResponse<Unit> {
        return try {
            val res = api.changeRoleByLogin(
                RoleRequest(
                    UserConfig.getLogin(),
                    accessLevel = accessLevel
                )
            )
           // UserConfig.setAccessLevel(res)
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}