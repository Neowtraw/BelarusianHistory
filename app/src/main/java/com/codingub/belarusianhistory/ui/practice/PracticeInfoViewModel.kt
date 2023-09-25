package com.codingub.belarusianhistory.ui.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.domain.model.PracticeQuestion
import com.codingub.belarusianhistory.domain.model.TicketQuestion
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PracticeInfoViewModel @Inject constructor() : ViewModel() {

    private val _ticketQuestion = MutableLiveData<TicketQuestion>()
    val ticketQuestion : LiveData<TicketQuestion> get() = _ticketQuestion

    private var position : Int = 0


    //для передачи в новый фрагмент
    private val _resultList = MutableLiveData<List<UserPracticeAnswer>>()
    val resultList : LiveData<List<UserPracticeAnswer>> get() = _resultList

    fun selectTicketQuestion(ticketQuestion: TicketQuestion){
        _ticketQuestion.value = ticketQuestion
    }

    fun getPracticeQuestionByPosition() : PracticeQuestion {
        return _ticketQuestion.value!!.practiceList[position].also {
            changePosition()
        }
    }

    // !! так как вызывается уже после обновления model
    private fun changePosition(){
        if(_ticketQuestion.value!!.practiceList.size != position) position += 1
    }

    fun addUserResult(userAnswer: List<String>){
        val currentList = _resultList.value ?: emptyList()
        val newList = currentList.toMutableList()
        val rightAnswer = _ticketQuestion.value!!.practiceList[position].answers.map {
            // преобразуем в лист string для упрощенной передачи и вывода
            it.answerName
        }

        newList.add(
            UserPracticeAnswer(
            pqInfo = _ticketQuestion.value!!.practiceList[position].info,
                userAnswer = userAnswer,
                rightAnswer = rightAnswer,
                isRight = userAnswer == rightAnswer
        )
        )
        _resultList.value = newList
    }

}