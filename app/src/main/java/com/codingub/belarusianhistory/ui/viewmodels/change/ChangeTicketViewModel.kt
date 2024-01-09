package com.codingub.belarusianhistory.ui.viewmodels.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.DeleteTicketsByIdsUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetAllTicketsUseCase
import com.codingub.belarusianhistory.domain.use_cases.InsertTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeTicketViewModel @Inject constructor(
    private val getAllTicketsUseCase: GetAllTicketsUseCase,
    private val insertTicketUseCase: InsertTicketUseCase,
    private val deleteTicketsByIdsUseCase: DeleteTicketsByIdsUseCase
) : ViewModel() {

    // insert or update
    private val _isTicketInserted =
        MutableStateFlow<InsertedUiState>(InsertedUiState.Loading)
    val isTicketInserted = _isTicketInserted.asStateFlow()

    // delete
    private val _isTicketsDeleted =
        MutableStateFlow<DeletedUiState>(DeletedUiState.Loading)
    val isTicketsDeleted = _isTicketsDeleted.asStateFlow()

    // initialize
    private val _tickets =
        MutableStateFlow<ServerResponse<List<TicketDto>>>(ServerResponse.Loading())
    val tickets = _tickets.asStateFlow()

    var isRemoved: Boolean = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = getAllTicketsUseCase()
            _tickets.value = data
        }
    }

    fun saveChanges(ticket: TicketDto) {
        viewModelScope.launch {
            val data = insertTicketUseCase(
                name = ticket.name, timer = ticket.timer,
                achieve = ticket.achievement
            )
            _isTicketInserted.value = when (data) {
                is ServerResponse.OK -> InsertedUiState.Inserted
                is ServerResponse.Error -> InsertedUiState.Failed(data.errorMessage)
                else -> InsertedUiState.Loading
            }
        }
    }

    fun setDeleteState(state: DeletedUiState) {
        _isTicketsDeleted.value = state
    }

    // deleted
    fun deleteTickets(tickets: List<TicketDto>) {
        viewModelScope.launch {
            val data = deleteTicketsByIdsUseCase(tickets.map { it.id })
            _isTicketsDeleted.value = when (data) {
                is ServerResponse.OK -> DeletedUiState.Deleted
                is ServerResponse.Error -> DeletedUiState.Failed(data.errorMessage)
                else -> DeletedUiState.Loading
            }
        }
    }
}
