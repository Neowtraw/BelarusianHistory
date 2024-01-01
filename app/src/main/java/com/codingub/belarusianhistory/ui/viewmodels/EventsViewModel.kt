package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.GetAllEventsUseCase
import com.codingub.belarusianhistory.sdk.models.events.EventDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase
) : ViewModel() {

    private val _events: MutableStateFlow<ServerResponse<List<EventDto>>> =
        MutableStateFlow(ServerResponse.Loading())

    val events = _events.asStateFlow()


    init {
        getAllEvents()
    }

    private fun getAllEvents() {
        viewModelScope.launch {
            val data = getAllEventsUseCase()
            _events.value = data
        }
    }
}