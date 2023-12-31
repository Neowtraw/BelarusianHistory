package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.domain.use_cases.GetAllTicketsUseCase
import com.codingub.belarusianhistory.utils.network.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val getAllTickets: GetAllTicketsUseCase
) : ViewModel() {

    private val _tickets: MutableStateFlow<List<TicketDto>> = MutableStateFlow(emptyList())
    val tickets = _tickets.asStateFlow()

    private val resultChannel = Channel<ServerResponse<List<TicketDto>>>()
    val ticketsState = resultChannel.receiveAsFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            NetworkManager.awaitNetworkConnection(resultChannel, ServerResponse.Loading(true))
            resultChannel.send(ServerResponse.Loading(true))
            val result = getAllTickets()
            resultChannel.send(result)
        }
    }
}