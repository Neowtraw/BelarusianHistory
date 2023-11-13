package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.domain.model.Achieves.Achieve
import com.codingub.belarusianhistory.domain.use_case.GetAchieves
import com.codingub.belarusianhistory.sdk.AchievesCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AchieveViewModel @Inject constructor(
    private val getAchieves: GetAchieves
) : ViewModel() {

    private val _displayedAchieves: MutableLiveData<List<Achieve>> = MutableLiveData()
    val displayedAchieves: LiveData<List<Achieve>> get() = _displayedAchieves


    suspend fun updateAchieves(achieve: AchievesCategory){
        val data: List<Achieve>

        try{
            data = getAchieves(achieve)
        } catch(e: Exception){
            //error handling
            return
        }

        if(data.isNotEmpty()){
            _displayedAchieves.postValue(data.sortedByDescending { it.isPassed })
        } else{
            //error handling
        }
    }


}