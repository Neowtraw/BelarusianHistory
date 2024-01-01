package com.codingub.belarusianhistory.domain.repos

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.sdk.models.practices.Answer
import com.codingub.belarusianhistory.sdk.models.practices.PracticeQuestion

interface PracticeQuestionRepository {
    suspend fun getPqByTqId(TqId: String) : ServerResponse<List<PracticeQuestion>>

    suspend fun insertPq(
        taskType: Int,
        name: String,
        info: String,
        answers: List<Answer>,
    ): ServerResponse<Unit>

    suspend fun deletePq(
        questionId: String
    ): ServerResponse<Unit>
}
