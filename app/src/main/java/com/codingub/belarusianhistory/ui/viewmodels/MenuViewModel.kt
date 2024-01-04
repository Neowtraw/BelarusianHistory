package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.local.prefs.UserConfig
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.GetAllAchievesUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetAllResultsUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetTypeResultsUseCase
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.models.userdata.ResultDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getAllAchievesUseCase: GetAllAchievesUseCase,
    private val getAllResultsUseCase: GetAllResultsUseCase
) : ViewModel() {

    private val _results = MutableStateFlow<ServerResponse<List<ResultDto>>>(ServerResponse.Loading())
    private val _achieves = MutableStateFlow<ServerResponse<List<AchieveDto>>>(ServerResponse.Loading())


    //practice
    val practice = combine(_results, _achieves) { results, achieves ->
        val practiceAchieves = achieves.data?.filter { it.type == AchieveType.PRACTICE } ?: emptyList()
        val practiceResults  = results.data?.filter { practiceAchieves.map{it.id}.contains(it.achieveId) } ?: emptyList()

        "${practiceResults.size}/${practiceAchieves.size}"
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = "0/0"
    )

    // achieves
    val achieves = combine(_results, _achieves) { results, achieves ->

        "${results.data?.size ?: 0}/${achieves.data?.size ?: 0}"
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = "0/0"
    )

    // tickets
    val tickets = combine(_results, _achieves) { results, achieves ->
        val ticketAchieves = achieves.data?.filter { it.type == AchieveType.TICKET } ?: emptyList()
        val ticketResults  = results.data?.filter { ticketAchieves.map{it.id}.contains(it.achieveId) } ?: emptyList()

        "${ticketResults.size}/${ticketAchieves.size}"
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = "0/0"
    )


    // get data for { results / achieves }
    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _achieves.value = getAllAchievesUseCase()
            _results.value = getAllResultsUseCase(userLogin = UserConfig.getLogin())
        }
    }
}