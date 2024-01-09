package com.codingub.belarusianhistory.ui.viewmodels.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.DeletePqsByIdsUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetPqByTqIdUseCase
import com.codingub.belarusianhistory.domain.use_cases.InsertPqUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePracticeViewModel @Inject constructor(
    private val getPqByTqIdUseCase: GetPqByTqIdUseCase,
    private val insertPqUseCase: InsertPqUseCase,
    private val deletePqsByIdsUseCase: DeletePqsByIdsUseCase
) : ViewModel() {

    private val _isPracticeDeleted = MutableStateFlow<DeletedUiState>(DeletedUiState.Loading)
    val isPracticeDeleted = _isPracticeDeleted.asStateFlow()

    private val _isPracticeInserted = MutableStateFlow<InsertedUiState>(InsertedUiState.Inserted)
    val isPracticeInserted = _isPracticeDeleted.asStateFlow()


    // initialize
    private val _pqs =
        MutableStateFlow<ServerResponse<List<PracticeQuestion>>>(ServerResponse.Loading())
    val pqs = _pqs.asStateFlow()

    var isRemoved: Boolean = false


    fun getPqs(tqId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = getPqByTqIdUseCase(tqId)
            _pqs.value = data
        }
    }

    fun saveChanges(tqId: String, pq: PracticeQuestion) {
        viewModelScope.launch {
            val data = insertPqUseCase(
                name = pq.name,
                info = pq.info,
                taskType = pq.taskType.ordinal,
                answers = pq.answers
            )
            _isPracticeInserted.value = when (data) {
                is ServerResponse.OK -> InsertedUiState.Inserted
                is ServerResponse.Error -> InsertedUiState.Failed(data.errorMessage)
                else -> InsertedUiState.Loading
            }
        }
    }

    fun setDeleteState(state: DeletedUiState) {
        _isPracticeDeleted.value = state
    }

    // deleted
    fun deleteTickets(tickets: List<TicketDto>) {
        viewModelScope.launch {
            val data = deletePqsByIdsUseCase(tickets.map { it.id })
            _isPracticeDeleted.value = when (data) {
                is ServerResponse.OK -> DeletedUiState.Deleted
                is ServerResponse.Error -> DeletedUiState.Failed(data.errorMessage)
                else -> DeletedUiState.Loading
            }
        }
    }
}

