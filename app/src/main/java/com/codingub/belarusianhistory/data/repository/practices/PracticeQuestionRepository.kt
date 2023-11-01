package com.codingub.belarusianhistory.data.repository.practices

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.practices.Answer
import com.codingub.belarusianhistory.data.remote.network.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.remote.network.requests.DeletePqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertPqRequest
import retrofit2.HttpException
import javax.inject.Inject

interface PracticeQuestionRepository {
    suspend fun getPqByTqId(TqId: String) : ServerResponse<List<PracticeQuestion>>

    suspend fun insertPq(
        taskType: Int,
        name: String,
        info: String,
        answers: List<Answer>,
    ): ServerResponse<Unit>

    suspend fun deletePq(
        tqId: String,
        questionId: String
    ): ServerResponse<Unit>
}


class PracticeQuestionRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : PracticeQuestionRepository {

    override suspend fun getPqByTqId(tqId: String): ServerResponse<List<PracticeQuestion>> {
        return try{
            val result = api.getPqByTqId(tqId = tqId)
            ServerResponse.OK(result.pqList)
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

    override suspend fun insertPq(
        taskType: Int,
        name: String,
        info: String,
        answers: List<Answer>
    ): ServerResponse<Unit>{
        return try{
            api.insertPq(InsertPqRequest(
                taskType = taskType,
                name = name,
                info = info,
                answers = answers
            ))
            ServerResponse.OK()
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

    override suspend fun deletePq(tqId: String, questionId: String): ServerResponse<Unit> {
        return try{
            api.deletePq(DeletePqRequest(
                tqId, questionId
            ))
            ServerResponse.OK()
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