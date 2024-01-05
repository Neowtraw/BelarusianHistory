package com.codingub.belarusianhistory.ui.viewmodels.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.domain.use_cases.GetAllTqUseCase
import com.codingub.belarusianhistory.utils.network.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val getAllTicketQuestions: GetAllTqUseCase
) : ViewModel() {

    private val resultChannel = Channel<ServerResponse<List<TicketQuestionDto>>>()
    val practiceState = resultChannel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            NetworkManager.awaitNetworkConnection(resultChannel, ServerResponse.Loading(true))
            resultChannel.send(ServerResponse.Loading(true))
            val result = getAllTicketQuestions()
            resultChannel.send(result)

        }
    }
}