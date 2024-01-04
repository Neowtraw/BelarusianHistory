package com.codingub.belarusianhistory.data.repos


import com.codingub.belarusianhistory.data.local.prefs.UserConfig
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RoleRequest
import com.codingub.belarusianhistory.domain.datasource.ResultLocalDataSource
import com.codingub.belarusianhistory.domain.datasource.ResultRemoteDataSource
import com.codingub.belarusianhistory.domain.repos.UserRepository
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.userdata.ResultDto
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi,
    private val localDataSource: ResultLocalDataSource,
    private val remoteDataSource: ResultRemoteDataSource
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
            if (e is CancellationException) throw e
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
            if (e is CancellationException) throw e
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
            if (e is CancellationException) throw e
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
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    /*
        Results
     */
    override suspend fun getTypeResults(userLogin: String, type: AchieveType): ServerResponse<List<ResultDto>> {
        return remoteDataSource.getTypeResults(userLogin, type)
    }

    override suspend fun setResult(
        userLogin: String,
        answer: Any?,
        achieve: AchieveDto
    ): ServerResponse<Unit> {
        return remoteDataSource.setResults(userLogin, answer, achieve)
    }

    override suspend fun resetResults(userLogin: String, type: AchieveType): ServerResponse<Unit> {
        return remoteDataSource.resetResults(userLogin, type)
    }

    override suspend fun getAllResults(userLogin: String): ServerResponse<List<ResultDto>> {
        return remoteDataSource.getAllResults(userLogin)
    }
}