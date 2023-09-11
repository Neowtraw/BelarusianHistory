package com.codingub.belarusianhistory.domain.use_case

import android.util.Log
import androidx.lifecycle.asLiveData
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTicketAchieves  @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke() : List<TicketAchieves>{
        return repository.getAllTicketAchieves()
    }
}