package com.codingub.belarusianhistory.presentation.ui.tickets_practice

import android.util.Log
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
    val practice = MutableLiveData<List<TicketQuestion>>()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val pracs = getAllTicketQuestions()
            practice.postValue(pracs)
        }
    }

}