package com.codingub.belarusianhistory.ui.viewmodels.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.models.map.MapDto
import com.codingub.belarusianhistory.data.models.map.MapLabelDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.AddLabelOnMapUseCase
import com.codingub.belarusianhistory.domain.use_cases.DeleteLabelFromMapUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetMapUseCase
import com.codingub.belarusianhistory.domain.use_cases.UpdateLabelOnMapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MenuEvent {
    data object LabelAdded : MenuEvent()
    data object LabelDeleted : MenuEvent()
    data class PeriodSelected(val periodId: String) : MenuEvent()
}

@HiltViewModel
class MapViewModel @Inject constructor(
    private val addLabelOnMapUseCase: AddLabelOnMapUseCase,
    private val updateLabelOnMapUseCase: UpdateLabelOnMapUseCase,
    private val deleteLabelFromMapUseCase: DeleteLabelFromMapUseCase,
    private val getMapUseCase: GetMapUseCase
) : ViewModel() {

    private val _isInfoShowed = MutableStateFlow(false)
    val isInfoShowed = _isInfoShowed.asStateFlow()

    private val _addedState = MutableStateFlow(LabelAddedState())
    val addedState = _addedState.asStateFlow()

    private val _deletedState = MutableStateFlow(LabelDeletedState())
    val deletedState = _deletedState.asStateFlow()

    private val _map: MutableStateFlow<ServerResponse<MapDto>> =
        MutableStateFlow(ServerResponse.Loading())
    val map = _map.asStateFlow()

    private fun getMap(periodId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _map.value = getMapUseCase(periodId)
        }
    }

    fun addLabel() {
        require(_addedState.value.isItemSelected) {
            viewModelScope.launch(Dispatchers.IO) {
                addLabelOnMapUseCase(_addedState.value.item ?: return@launch)
            }
        }
        setLabelAdded(false)
    }

    fun deleteLabel() {
        require(_deletedState.value.isItemSelected) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteLabelFromMapUseCase(_deletedState.value.id ?: return@launch)
            }
        }
    }

    /*
        Additional
     */
    fun setLabelDeleted(state: Boolean) = _deletedState.update {
        it.copy(isItemSelected = state)
    }

    fun setLabelAdded(state: Boolean) = _addedState.update {
        it.copy(isItemSelected = state)
    }

    fun setInfoShowed(state: Boolean) {
        _isInfoShowed.value = state
    }

    fun onEvent(event: MenuEvent) {
        when(event){
            is MenuEvent.LabelAdded -> {
                addLabel()
            }
            is MenuEvent.LabelDeleted -> {
                deleteLabel()
            }
            is MenuEvent.PeriodSelected -> {
                getMap(event.periodId)
            }
        }
    }

    fun onLabelDeletedEvent(event: LabelDeletedEvent) {
        when(event){
            is LabelDeletedEvent.OnLabelDeleted ->{
                _deletedState.update {
                    it.copy(id = event.id)
                }
            }
            is LabelDeletedEvent.OnErrorOccurred -> {
                _deletedState.update {
                    it.copy(onError = event.error)
                }
            }
            is LabelDeletedEvent.OnItemSelected -> {
                _deletedState.update {
                    it.copy(isItemSelected = event.state)
                }
            }
            else -> {}
        }
    }

    fun onLabelAddedEvent(event: LabelAddedEvent) {
        when (event) {
            is LabelAddedEvent.OnLabelAdded -> {
                    _addedState.update {
                        it.copy(
                            item = MapLabelDto(
                                title = event.title,
                                description = event.description,
                                animation = event.animation,
                                image = event.image,
                                creatorId = event.creatorId,
                                x = event.x,
                                y = event.y,
                                mapId = map.value.data!!.id
                            )
                        )
                    }
                    return
                }
            is LabelAddedEvent.OnErrorOccurred -> {
                _addedState.update {
                    it.copy(onError = event.error)
                }
            }
            is LabelAddedEvent.OnItemSelected -> {
                _addedState.update {
                    it.copy(isItemSelected = event.state)
                }
            }

            else -> {}
        }
    }


    data class LabelAddedState(
        var item: MapLabelDto? = null,
        var onError: String? = null,
        var isItemSelected: Boolean = false
    )

    data class LabelDeletedState(
        var id: String? = null,
        var onError: String? = null,
        var isItemSelected: Boolean = false
    )

    sealed class LabelDeletedEvent {
        data class OnLabelDeleted(val id: String) : LabelDeletedEvent()
        data class OnErrorOccurred(val error: String?) : LabelAddedEvent()
        data class OnItemSelected(val state: Boolean) : LabelAddedEvent()
    }


    sealed class LabelAddedEvent {
        data class OnLabelAdded(
            val x: Float,
            val y: Float,
            val title: String,
            val description: String,
            val animation: String? = null,
            val image: String? = null,
            val creatorId: String
        ) : LabelAddedEvent()
        data class OnErrorOccurred(val error: String?) : LabelAddedEvent()
        data class OnItemSelected(val state: Boolean) : LabelAddedEvent()
    }
}