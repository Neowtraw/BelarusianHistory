package com.codingub.belarusianhistory.ui.tickets_practice.tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.domain.model.Ticket
import com.codingub.belarusianhistory.domain.use_case.GetAllTickets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val getAllTickets: GetAllTickets
) : ViewModel() {

    private val _tickets = MutableLiveData<List<Ticket>>()
    val tickets : LiveData<List<Ticket>> get() = _tickets

    init{

        viewModelScope.launch(Dispatchers.IO) {
            val ticks = getAllTickets()
            _tickets.postValue(ticks)
        }
    }
}