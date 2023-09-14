package com.codingub.belarusianhistory.presentation.ui.achieves

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.domain.model.Achieves.PracticeAchieves
import com.codingub.belarusianhistory.domain.model.Achieves.TicketAchieves
import com.codingub.belarusianhistory.domain.use_case.GetAllPracticeAchieves
import com.codingub.belarusianhistory.domain.use_case.GetAllTicketAchieves
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchieveViewModel @Inject constructor(
    private val getAllTicketAchieves: GetAllTicketAchieves,
    private val getAllPracticeAchieves: GetAllPracticeAchieves
) : ViewModel() {

    val allTicketAchieves = MutableLiveData<List<TicketAchieves>>()
    val allPracticeAchieves = MutableLiveData<List<PracticeAchieves>>()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            val tAchieves = getAllTicketAchieves()
            val sortedAchieves = tAchieves.sortedBy { it.isPassed }
            allTicketAchieves.postValue(sortedAchieves)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val pAchieves = getAllPracticeAchieves()
            val sortedAchieves = pAchieves.sortedBy { it.isPassed }
            allPracticeAchieves.postValue(sortedAchieves)
        }
    }


}