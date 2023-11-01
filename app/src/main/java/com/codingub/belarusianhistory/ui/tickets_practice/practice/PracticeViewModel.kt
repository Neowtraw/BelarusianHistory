package com.codingub.belarusianhistory.ui.tickets_practice.practice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.domain.use_cases.GetAllTqUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val getAllTicketQuestions: GetAllTqUseCase
) : ViewModel(){

    private val resultChannel = Channel<ServerResponse<List<TicketQuestion>>>()
    val practiceState = resultChannel.receiveAsFlow()

    // Для отображения существующей практики.. Можно добавить проверку сразу на сервере
    // getAllTicketQuestions().data?.filter { it.practices.isNotEmpty() }

    init{
        getTicketQuestions()
    }

    fun getTicketQuestions(){
        viewModelScope.launch(Dispatchers.IO) {
            resultChannel.send(ServerResponse.Loading(true))
            val result = getAllTicketQuestions()
            resultChannel.send(result)
        }
    }

}