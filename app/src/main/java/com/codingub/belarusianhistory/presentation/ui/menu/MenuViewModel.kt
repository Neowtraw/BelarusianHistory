package com.codingub.belarusianhistory.presentation.ui.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.domain.use_case.GetAllPracticeAchieves
import com.codingub.belarusianhistory.domain.use_case.GetAllTicketAchieves
import com.codingub.belarusianhistory.domain.use_case.GetPracticeAchievesByPassed
import com.codingub.belarusianhistory.domain.use_case.GetTicketAchievesByPassed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getAllPracticeAchieves : GetAllPracticeAchieves,
    private val getAllTicketAchieves: GetAllTicketAchieves,
    private val getTicketAchievesByPassed: GetTicketAchievesByPassed,
    private val getPracticeAchievesByPassed: GetPracticeAchievesByPassed
) : ViewModel() {

    //чтобы передавать в код
    val ticketAchievesPassed = MutableLiveData<Int>()
    val practiceAchievesPassed = MutableLiveData<Int>()
    val ticketAchieves = MutableLiveData<Int>()
    val practiceAchieves = MutableLiveData<Int>()

    var allAchieves = MutableLiveData<Int>()
    var allAchievesPassed = MutableLiveData<Int>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val passedTicketAchieves = getTicketAchievesByPassed(1)
            ticketAchievesPassed.postValue(passedTicketAchieves.size)

            val passedPracticeAchieves = getPracticeAchievesByPassed(1)
            practiceAchievesPassed.postValue(passedPracticeAchieves.size)

            allAchievesPassed.postValue(passedPracticeAchieves.size
                    + passedTicketAchieves.size)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val tickAchieves = getAllTicketAchieves()
            ticketAchieves.postValue(tickAchieves.size)

            val pracAchieves = getAllPracticeAchieves()
            practiceAchieves.postValue(pracAchieves.size)

            allAchieves.postValue(pracAchieves.size + tickAchieves.size)
        }

    }

}