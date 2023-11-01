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
            if(e.code() == 400){
                ServerResponse.BadRequest(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else if(e.code() == 404) {
                ServerResponse.NotFound()
            } else if(e.code() == 409) {
                ServerResponse.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            }
            else{
                ServerResponse.UnknownError()
            }
        } catch (e: Exception){
            ServerResponse.UnknownError()
        }
    }

    override suspend fun getTypeAchieves(type: AchievesCategory) : ServerResponse<List<Achieve>>{
        return try{
            val result = api.getTypeAchieves(type = type.ordinal)
            ServerResponse.OK(result.achieveList)
        } catch (e: HttpException){
            if(e.code() == 400){
                ServerResponse.BadRequest(e.response()?.errorBody()?.string() ?: "Unknown error")
            } else if(e.code() == 404) {
                ServerResponse.NotFound()
            } else if(e.code() == 409) {
                ServerResponse.Conflict(e.response()?.errorBody()?.string() ?: "Unknown error")
            }
            else{
                ServerResponse.UnknownError()
            }
        } catch (e: Exception){
            ServerResponse.UnknownError()
        }
    }
}