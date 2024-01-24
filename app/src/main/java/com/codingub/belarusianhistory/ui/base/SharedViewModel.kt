package com.codingub.belarusianhistory.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.data.models.map.MapTypeDto
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


//used to assign the data between fragments
class SharedViewModel : ViewModel() {

    private val _practiceInfo = MutableLiveData<TicketQuestionDto>()
    val practiceInfo : LiveData<TicketQuestionDto> get() = _practiceInfo

    private val _ticketInfo = MutableLiveData<TicketDto>()
    val ticketInfo : LiveData<TicketDto> get() = _ticketInfo

    private val _mapType = MutableStateFlow<MapTypeDto?>(null)
    val mapType = _mapType.asStateFlow()

    fun select(mapType: MapTypeDto) {
        _mapType.value = mapType
    }

    fun select(ticket: TicketDto){
        _ticketInfo.value = ticket
    }

    fun select(practice: TicketQuestionDto){
        _practiceInfo.value = practice
    }
}