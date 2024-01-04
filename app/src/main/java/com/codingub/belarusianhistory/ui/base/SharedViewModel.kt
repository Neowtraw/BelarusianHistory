package com.codingub.belarusianhistory.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.ui.adapters.change.ChangeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//used to assign the data between fragments
class SharedViewModel : ViewModel() {

    private val _practiceInfo = MutableLiveData<TicketQuestionDto>()
    val practiceInfo : LiveData<TicketQuestionDto> get() = _practiceInfo

    private val _ticketInfo = MutableLiveData<TicketDto>()
    val ticketInfo : LiveData<TicketDto> get() = _ticketInfo

    private val _changeType = MutableStateFlow(ChangeType.TICKETS)
    val changeType = _changeType.asStateFlow()

    fun select(type: ChangeType) {
        _changeType.value = type
    }

    fun select(ticket: TicketDto){
        _ticketInfo.value = ticket
    }

    fun select(practice: TicketQuestionDto){
        _practiceInfo.value = practice
    }
}