package com.codingub.belarusianhistory.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.model.TicketQuestion

//используется чисто для передачи данных между фрагментами
class SharedViewModel : ViewModel() {


    private val _practiceInfo = MutableLiveData<TicketQuestion>()
    val practiceInfo : LiveData<TicketQuestion> get() = _practiceInfo

    private val _ticketInfo = MutableLiveData<Ticket>()
    val ticketInfo : LiveData<Ticket> get() = _ticketInfo

    fun select(ticket: Ticket){
        _ticketInfo.value = ticket
    }

    fun select(practice: TicketQuestion){
        _practiceInfo.value = practice
    }
}