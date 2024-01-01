package com.codingub.belarusianhistory.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.sdk.models.tickets.TicketDto
import com.codingub.belarusianhistory.sdk.models.tickets.TicketQuestionDto

//used to assign the data between fragments
class SharedViewModel : ViewModel() {


    private val _practiceInfo = MutableLiveData<TicketQuestionDto>()
    val practiceInfo : LiveData<TicketQuestionDto> get() = _practiceInfo

    private val _ticketInfo = MutableLiveData<TicketDto>()
    val ticketInfo : LiveData<TicketDto> get() = _ticketInfo

    fun select(ticket: TicketDto){
        _ticketInfo.value = ticket
    }

    fun select(practice: TicketQuestionDto){
        _practiceInfo.value = practice
    }
}