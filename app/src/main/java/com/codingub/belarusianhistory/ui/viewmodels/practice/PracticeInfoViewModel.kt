package com.codingub.belarusianhistory.ui.viewmodels.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PracticeInfoViewModel @Inject constructor() : ViewModel() {

    private val _ticketQuestion = MutableLiveData<TicketQuestionDto>()
    val ticketQuestion : LiveData<TicketQuestionDto> get() = _ticketQuestion

    private var position : Int = 0


    //для передачи в новый фрагмент
    private val _resultList = MutableLiveData<MutableList<UserPracticeAnswer>>()
    val resultList : LiveData<MutableList<UserPracticeAnswer>> get() = _resultList

    fun selectTicketQuestion(ticketQuestion: TicketQuestionDto){
        _ticketQuestion.value = ticketQuestion
    }

    fun getPracticeQuestionByPosition() : PracticeQuestion? {
        val ticketQuestion = _ticketQuestion.value
        return if (ticketQuestion != null && ticketQuestion.practices!!.size > position) {
            val practiceQuestion = ticketQuestion.practices[position]
            changePosition()
            practiceQuestion
        } else {
            null
        }
    }

    // !! так как вызывается уже после обновления model
    private fun changePosition(){
        if(_ticketQuestion.value != null ||
            _ticketQuestion.value!!.practices.size != position) position += 1
    }

    fun addUserResult(userAnswer: UserPracticeAnswer) {
        val resultList = _resultList.value?.toMutableList() ?: mutableListOf()
        resultList.add(userAnswer)
        _resultList.value = resultList
    }

}