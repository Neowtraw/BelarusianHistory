package com.codingub.belarusianhistory.data.repos

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.repos.AchieveRepository
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.sdk.AchieveType
import retrofit2.HttpException
import javax.inject.Inject


class AchieveRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : AchieveRepository {

    override suspend fun getAllAchieves() : ServerResponse<List<AchieveDto>>{
        return try{
            val result = api.getAllAchieves()
            ServerResponse.OK(result)
        } catch (e: HttpException){
                ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getTypeAchieves(type: AchieveType) : ServerResponse<List<AchieveDto>>{
        return try{
            val result = api.getTypeAchieves(type = type)
            ServerResponse.OK(result.achieveList)
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}