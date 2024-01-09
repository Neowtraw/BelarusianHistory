package com.codingub.belarusianhistory.data.repos

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.models.practices.Answer
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.remote.network.requests.ResetPqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertPqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetPqsRequest
import com.codingub.belarusianhistory.domain.repos.PracticeQuestionRepository
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PracticeQuestionRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : PracticeQuestionRepository {

    override suspend fun getPqByTqId(tqId: String): ServerResponse<List<PracticeQuestion>> {
        return try{
            val result = api.getPqByTqId(tqId = tqId)
            ServerResponse.OK(result.pqList)
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            if(e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
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
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            if(e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deletePq(questionId: String): ServerResponse<Unit> {
        return try{
            api.resetPq(ResetPqRequest(questionId))
            ServerResponse.OK()
        } catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            if(e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deletePqsByIds(ids: List<String>): ServerResponse<Unit> {
        return try {
            api.resetPqsByIds(ResetPqsRequest(ids))
            ServerResponse.OK()
        }catch (e: HttpException){
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception){
            if(e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }
}