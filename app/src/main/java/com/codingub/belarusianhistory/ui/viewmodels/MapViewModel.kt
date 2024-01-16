package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.models.map.MapDto
import com.codingub.belarusianhistory.data.models.map.MapItemDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemAddedState(
    var item: MapItemDto? = null,
    var onError: String? = null,
    var isItemAdded: Boolean = false
)

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val _isInfoShowed = MutableStateFlow(false)
    val isInfoShowed = _isInfoShowed.asStateFlow()

    private val _addedState = MutableStateFlow(ItemAddedState())
    val addedState = _addedState.asStateFlow()

    private val _map: MutableStateFlow<ServerResponse<MapDto>> =
        MutableStateFlow(ServerResponse.Loading())
    val map = _map.asStateFlow()

    init {
        getMap()
    }

    private fun getMap() {
        viewModelScope.launch(Dispatchers.IO) {
            // getMapUseCase(... period)
            // map.update { it }
        }
    }

    fun interactWithLabelInfo() {
        _isInfoShowed.value = !isInfoShowed.value

        if (_isInfoShowed.value) {

            return
        }
    }

    fun addLabel() {
        require(_addedState.value.isItemAdded){

        }
        setLabelAdded(false)
    }

    fun deleteLabel() {

    }

    /*
        Additional
     */
    fun setLabelAdded(state: Boolean) = _addedState.update {
        it.copy(isItemAdded = state)
    }

    fun setInfoShowed(state: Boolean) {
        _isInfoShowed.value = state
    }
}