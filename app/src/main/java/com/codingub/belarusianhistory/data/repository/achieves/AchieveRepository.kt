package com.codingub.belarusianhistory.data.repository.achieves

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.sdk.AchievesCategory
import retrofit2.HttpException
import javax.inject.Inject

interface AchieveRepository {

   suspend fun getAllAchieves() : ServerResponse<List<Achieve>>
   suspend fun getTypeAchieves(type: AchievesCategory) : ServerResponse<List<Achieve>>
}

class AchieveRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : AchieveRepository{

    override suspend fun getAllAchieves() : ServerResponse<List<Achieve>>{
        return try{
            val result = api.getAllAchieves()
            ServerResponse.OK(result)
        } catch (e: HttpException){
                ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getTypeAchieves(type: AchievesCategory) : ServerResponse<List<Achieve>>{
        return try{
            val result = api.getTypeAchieves(type = type.ordinal)
            ServerResponse.OK(result.achieveList)
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}