package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.practices.Answer
import com.codingub.belarusianhistory.data.remote.network.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.repository.practices.PracticeQuestionRepository
import javax.inject.Inject

class GetPqByTqIdUseCase @Inject constructor(private val repository: PracticeQuestionRepository) {

    suspend operator fun invoke(tqId: String): ServerResponse<List<PracticeQuestion>> {
        return repository.getPqByTqId(tqId)
    }
}

class InsertPqUseCase @Inject constructor(private val repository: PracticeQuestionRepository) {

    suspend operator fun invoke(
        taskType: Int,
        name: String,
        info: String,
        answers: List<Answer>
    ): ServerResponse<Unit> {
        return repository.insertPq(taskType, name, info, answers)
    }
}

class ResetPqUseCase @Inject constructor(private val repository: PracticeQuestionRepository) {

    suspend operator fun invoke(questionId: String): ServerResponse<Unit> {
        return repository.deletePq(questionId)
    }
}