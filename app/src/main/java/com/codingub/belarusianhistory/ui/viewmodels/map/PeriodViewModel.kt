package com.codingub.belarusianhistory.ui.viewmodels.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.models.map.MapTypeDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.GetMapTypesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeriodViewModel @Inject constructor(
    private val getMapTypesUseCase: GetMapTypesUseCase
) : ViewModel() {

    private val _types: MutableStateFlow<ServerResponse<List<MapTypeDto>>> =
        MutableStateFlow(ServerResponse.Loading())
    val types = _types.asStateFlow()

    init {
        getTypes()
    }

    private fun getTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            _types.value = getMapTypesUseCase()
        }
    }
}