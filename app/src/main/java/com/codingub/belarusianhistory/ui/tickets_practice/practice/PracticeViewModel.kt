package com.codingub.belarusianhistory.ui.tickets_practice.practice

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
            val filteredPracs = getAllTicketQuestions().filter { !it.practiceList.isNullOrEmpty() }
            _practice.postValue(filteredPracs)
        }
    }

}