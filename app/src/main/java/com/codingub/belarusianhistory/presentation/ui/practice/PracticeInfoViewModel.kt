package com.codingub.belarusianhistory.presentation.ui.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PracticeInfoViewModel @Inject constructor() : ViewModel() {

    private val _ticketQuestion = MutableLiveData<TicketQuestion>()
    val ticketQuestion : LiveData<TicketQuestion> get() = _ticketQuestion


    fun select(ticketQuestion: TicketQuestion){
        _ticketQuestion.value = ticketQuestion
    }

}