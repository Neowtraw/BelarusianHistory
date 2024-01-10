package com.codingub.belarusianhistory.ui.viewmodels.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.DeleteTqsByIdsUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetAllTqUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetTqByTicketIdUseCase
import com.codingub.belarusianhistory.domain.use_cases.InsertTqUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeTqViewModel @Inject constructor(
    private val getTqByTicketIdUseCase: GetTqByTicketIdUseCase,
    private val insertTqUseCase: InsertTqUseCase,
    private val deleteTqsByIdsUseCase: DeleteTqsByIdsUseCase
) : ViewModel() {

    private val _isTqDeleted = MutableStateFlow<DeletedUiState>(DeletedUiState.Loading)
    val isTqDeleted = _isTqDeleted.asStateFlow()

    private val _isTqInserted = MutableStateFlow<InsertedUiState>(InsertedUiState.Inserted)
    val isTqInserted = _isTqInserted.asStateFlow()

    // initialize
    private val _tqs =
        MutableStateFlow<ServerResponse<List<TicketQuestionDto>>>(ServerResponse.Loading())
    val tqs = _tqs.asStateFlow()

    var isRemoved: Boolean = false

    fun getTqs(ticketId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _tqs.value = getTqByTicketIdUseCase(ticketId)
        }
    }

    fun saveChanges(ticketId: String, tq: TicketQuestionDto) {
        viewModelScope.launch {
            val data = insertTqUseCase(
                name = tq.name,
                info = tq.info,
                achieve = tq.achieve,
                ticketId = ticketId
            )
            _isTqInserted.value = when (data) {
                is ServerResponse.OK -> InsertedUiState.Inserted
                is ServerResponse.Error -> InsertedUiState.Failed(data.errorMessage)
                else -> InsertedUiState.Loading
            }
        }
    }

    fun setDeleteState(state: DeletedUiState) {
        _isTqDeleted.value = state
    }

    // deleted
    fun deleteItems(items: List<TicketQuestionDto>) {
        viewModelScope.launch {
            val data = deleteTqsByIdsUseCase(items.map { it.id })
            _isTqDeleted.value = when (data) {
                is ServerResponse.OK -> DeletedUiState.Deleted
                is ServerResponse.Error -> DeletedUiState.Failed(data.errorMessage)
                else -> DeletedUiState.Loading
            }
        }
    }
}

