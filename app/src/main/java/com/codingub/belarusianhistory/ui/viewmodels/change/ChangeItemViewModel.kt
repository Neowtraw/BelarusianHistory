package com.codingub.belarusianhistory.ui.viewmodels.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.models.tickets.TicketDto
import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.GetAllTicketsUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetAllTqUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeItemViewModel @Inject constructor(
    private val getAllTqUseCase: GetAllTqUseCase
) : ViewModel() {

    // initialize
    private val _practice by lazy { MutableStateFlow<List<PracticeQuestion>>(emptyList()) }
    private val _tqs =
        MutableStateFlow<ServerResponse<List<TicketQuestionDto>>>(ServerResponse.Loading())

    private val _changeType = MutableStateFlow(ChangeType.TQS)
    private val _isChanged = MutableStateFlow<MutableMap<ChangeType, Boolean>>(mutableMapOf())

    val isRemoved: Boolean = false

    val practice by lazy { _practice.asStateFlow() }
    val tqs by lazy { _tqs.asStateFlow() }

    val isChanged = _isChanged.map { result -> result.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setChanged() {
        _isChanged.value[_changeType.value] = true
    }

    init {
        viewModelScope.launch {
            _tqs.value = getAllTqUseCase()
        }
    }

    fun setChangeType(type: ChangeType, id: String = "") {
        if (type == ChangeType.PRACTICE) _practice.value =
            _tqs.value.data!!.find { it.id == id }!!.practices

        _changeType.value = type
    }

    fun saveChanges(): Boolean {
        _isChanged.value.forEach { (type, _) ->
            when (type) {
                ChangeType.PRACTICE -> {
                    viewModelScope.launch {
                        // insert
                    }
                }

                ChangeType.TQS -> {
                    viewModelScope.launch {
                        // insert
                    }
                }
            }
        }
        return true // temporary
    }

    //deleted
//    fun deleteElements(practice: List<PracticeQuestion>) {
//        require(isRemoved) {
//            // delete practice
//            // проверка на удаление
//        }
//    }
    fun deleteElements(tqs: List<TicketQuestionDto>) {
        require(isRemoved) {
            // delete tqs
            // проверка на удаление
        }
    }
}

enum class ChangeType {
    PRACTICE,
    TQS
}