package com.codingub.belarusianhistory.ui.viewmodels.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ResultInfoViewModel @Inject constructor() : ViewModel() {

    private val _resultList = MutableLiveData<List<UserPracticeAnswer>>()
    val resultList : LiveData<List<UserPracticeAnswer>> get() = _resultList

    var allAnswerCount = 0
    var userAnswerCount = 0


    fun select(result: List<UserPracticeAnswer>){
        _resultList.value = result
        userAnswerCount = result.count { it.isRight }

    }


}