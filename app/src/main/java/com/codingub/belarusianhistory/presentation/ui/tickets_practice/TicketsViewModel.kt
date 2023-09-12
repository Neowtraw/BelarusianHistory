package com.codingub.belarusianhistory.presentation.ui.tickets_practice

import android.util.Log
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

    val tickets = MutableLiveData<List<Ticket>>()

    init{

        viewModelScope.launch(Dispatchers.IO) {
            val ticks = getAllTickets()
            tickets.postValue(ticks)
            Log.d("SSSSSSSSSSSSSSSSSS", ticks.size.toString())
        }
    }
}