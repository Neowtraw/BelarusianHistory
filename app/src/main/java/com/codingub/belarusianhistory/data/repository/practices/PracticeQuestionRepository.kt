package com.codingub.belarusianhistory.data.repository.practices

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.models.practices.Answer
import com.codingub.belarusianhistory.data.remote.network.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.remote.network.requests.DeletePqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertPqRequest
import javax.inject.Inject

interface PracticeQuestionRepository {
//    suspend fun getAllPq(
//        tqId: String
//    ) : List<PracticeQuestion>
//
//    suspend fun insertPq(
//        taskType: Int,
//        name: String,
//        info: String,
//        answers: List<Answer>,
//    ) : String
//
//    suspend fun deletePq(
//        tqId: String,
//        questionId: String
//    ) : String
}

class PracticeQuestionRepositoryImpl @Inject constructor(
    private val api : HistoryAppApi
) : PracticeQuestionRepository{

//    override suspend fun getAllPq(tqId: String): List<PracticeQuestion> {
//        return api.getAllPq(tqId = tqId).data
//    }
//
//    override suspend fun insertPq(
//        taskType: Int,
//        name: String,
//        info: String,
//        answers: List<Answer>
//    ): String {
//        return api.insertPq(InsertPqRequest(
//            taskType = taskType,
//            name = name,
//            info = info,
//            answers = answers
//        )).message
//    }
//
//    override suspend fun deletePq(tqId: String, questionId: String): String {
//        return api.deletePq(DeletePqRequest(
//            tqId, questionId
//        )).message
//    }
}