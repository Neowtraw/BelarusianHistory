package com.codingub.belarusianhistory.presentation.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.domain.model.Ticket

class SharedViewModel : ViewModel() {

    val ticketInfo = MutableLiveData<Ticket>()

    fun select(ticket: Ticket){
        ticketInfo.value = ticket
    }
}