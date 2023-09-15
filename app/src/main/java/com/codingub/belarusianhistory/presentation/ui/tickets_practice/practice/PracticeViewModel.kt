package com.codingub.belarusianhistory.presentation.ui.tickets_practice.practice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.domain.use_case.GetAllTicketQuestions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val getAllTicketQuestions: GetAllTicketQuestions
) : ViewModel(){

    private val _practice = MutableLiveData<List<TicketQuestion>>()
    val practice : LiveData<List<TicketQuestion>> get() = _practice

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val pracs = getAllTicketQuestions()
            _practice.postValue(pracs)
        }
    }

}