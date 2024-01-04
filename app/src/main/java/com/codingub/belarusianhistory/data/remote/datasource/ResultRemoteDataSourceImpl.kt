package com.codingub.belarusianhistory.data.remote.datasource

import android.util.Log
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.AddResultRequest
import com.codingub.belarusianhistory.data.remote.network.requests.GetAllResultsRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetResultRequest
import com.codingub.belarusianhistory.domain.datasource.ResultRemoteDataSource
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.userdata.ResultDto
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class ResultRemoteDataSourceImpl @Inject constructor(
    private val api: HistoryAppApi
) : ResultRemoteDataSource {

    override suspend fun getTypeResults(
        userLogin: String,
        type: AchieveType
    ): ServerResponse<List<ResultDto>> {
        return try {
            val data = api.getTypeResults(
                login = userLogin,
                type = type
            )
            ServerResponse.OK(data.results)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getAllResults(userLogin: String): ServerResponse<List<ResultDto>> {
        return try {
            val data = api.getAllResults(
                GetAllResultsRequest(
                    login = userLogin
                )
            )
            ServerResponse.OK(data.results)
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun setResults(
        userLogin: String,
        answer: Any?,
        achieve: AchieveDto
    ): ServerResponse<Unit> {
        return try {
            api.setResult(
                AddResultRequest(
                    login = userLogin,
                    answer = answer,
                    achieve = achieve
                )
            )
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun resetResults(userLogin: String, type: AchieveType): ServerResponse<Unit> {
        return try {
            api.deleteResult(
                ResetResultRequest(
                    userLogin = userLogin,
                    type = type
                )
            )
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}