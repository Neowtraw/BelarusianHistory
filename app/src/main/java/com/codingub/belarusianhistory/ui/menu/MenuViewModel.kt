package com.codingub.belarusianhistory.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val _ticketAchievesPassed = MutableLiveData<Int>()
    private val _practiceAchievesPassed = MutableLiveData<Int>()
    private val _ticketAchieves = MutableLiveData<Int>()
    private val _practiceAchieves = MutableLiveData<Int>()
    private val _allAchieves = MutableLiveData<Int>()
    private val _allAchievesPassed = MutableLiveData<Int>()


    //чтобы передавать в код
    val ticketAchievesPassed : LiveData<Int> get() = _ticketAchievesPassed
    val practiceAchievesPassed : LiveData<Int> get() = _practiceAchievesPassed
    val ticketAchieves : LiveData<Int> get() = _ticketAchieves
    val practiceAchieves : LiveData<Int> get() = _practiceAchieves
    val allAchieves : LiveData<Int> get() = _allAchieves
    val allAchievesPassed : LiveData<Int> get() = _allAchievesPassed


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val passedTicketAchieves = getTicketAchievesByPassed(1)
            _ticketAchievesPassed.postValue(passedTicketAchieves.size)

            val passedPracticeAchieves = getPracticeAchievesByPassed(1)
            _practiceAchievesPassed.postValue(passedPracticeAchieves.size)

            _allAchievesPassed.postValue(passedPracticeAchieves.size
                    + passedTicketAchieves.size)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val tickAchieves = getAllTicketAchieves()
            _ticketAchieves.postValue(tickAchieves.size)

            val pracAchieves = getAllPracticeAchieves()
            _practiceAchieves.postValue(pracAchieves.size)

            _allAchieves.postValue(pracAchieves.size + tickAchieves.size)
        }

    }

}