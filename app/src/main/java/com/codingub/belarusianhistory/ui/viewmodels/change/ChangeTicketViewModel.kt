package com.codingub.belarusianhistory.ui.viewmodels.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.GetAllTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeTicketViewModel @Inject constructor(
    private val getAllTicketsUseCase: GetAllTicketsUseCase,
) : ViewModel() {

    // initialize
    private val _tickets =
        MutableStateFlow<ServerResponse<List<TicketDto>>>(ServerResponse.Loading())
    val tickets = _tickets.asStateFlow()

    private val _isChanged = MutableStateFlow(false)
    val isChanged = _isChanged.asStateFlow()
    var isRemoved: Boolean = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = getAllTicketsUseCase()
            _tickets.value = data
        }
    }

    fun setChanged() { _isChanged.value = true }

    fun saveChanges(): Boolean {
        viewModelScope.launch {
            // insert
        }

        return true
    }

    // deleted
    fun deleteTickets(tickets: List<TicketDto>) {
        require(isRemoved) {
            viewModelScope.launch {
                // delete tickets

            }
        }
    }
}
