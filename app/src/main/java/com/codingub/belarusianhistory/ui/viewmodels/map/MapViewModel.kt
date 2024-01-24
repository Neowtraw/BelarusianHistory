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
    data object LabelUpdated : MenuEvent()
    data class PeriodSelected(val periodId: String) : MenuEvent()
}

@HiltViewModel
class MapViewModel @Inject constructor(
    private val addLabelOnMapUseCase: AddLabelOnMapUseCase,
    private val updateLabelOnMapUseCase: UpdateLabelOnMapUseCase,
    private val deleteLabelFromMapUseCase: DeleteLabelFromMapUseCase,
    private val getMapUseCase: GetMapUseCase
) : ViewModel() {

    private val _updatedState = MutableStateFlow(LabelUpdatedState())
    val updatedState = _updatedState.asStateFlow()

    private val _addedState = MutableStateFlow(LabelAddedState())
    val addedState = _addedState.asStateFlow()

    private val _deletedState = MutableStateFlow(LabelDeletedState())
    val deletedState = _deletedState.asStateFlow()

    private val _map: MutableStateFlow<ServerResponse<MapDto>> =
        MutableStateFlow(ServerResponse.Loading())
    val map = _map.asStateFlow()

    fun getMap(periodId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _map.value = getMapUseCase(periodId)
        }
    }

    private fun addLabel() {
        require(_addedState.value.isItemAdded) {
            viewModelScope.launch(Dispatchers.IO) {
                onLabelAddedEvent(
                    LabelAddedEvent.OnResponseReceived(
                        addLabelOnMapUseCase(
                            _addedState.value.item ?: return@launch
                        )
                    )
                )
            }
        }
        onLabelAddedEvent(event = LabelAddedEvent.OnItemSelected(false))
    }

    private fun deleteLabel() {
        require(_deletedState.value.isItemDeleted) {
            viewModelScope.launch(Dispatchers.IO) {
                onLabelDeletedEvent(
                    LabelDeletedEvent.OnResponseReceived(
                        deleteLabelFromMapUseCase(_deletedState.value.id ?: return@launch)
                    )
                )
            }
        }
        onLabelDeletedEvent(event = LabelDeletedEvent.OnItemSelected(false))
    }

    private fun updateLabel() {
        require(_updatedState.value.isLabelInfoShowed) {
            viewModelScope.launch(Dispatchers.IO) {
                onLabelUpdatedEvent(
                    LabelUpdatedEvent.OnResponseReceived(
                        updateLabelOnMapUseCase(label = _updatedState.value.item ?: return@launch)
                    )
                )
            }
        }
        onLabelUpdatedEvent(event = LabelUpdatedEvent.OnLabelInfoShowed(false))
    }

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.LabelAdded -> {
                addLabel()
            }

            is MenuEvent.LabelDeleted -> {
                deleteLabel()
            }

            is MenuEvent.PeriodSelected -> {
                getMap(event.periodId)
            }

            is MenuEvent.LabelUpdated -> {
                updateLabel()
            }
        }
    }

    fun onLabelUpdatedEvent(event: LabelUpdatedEvent) {
        when (event) {
            is LabelUpdatedEvent.OnLabelUpdated -> {
                _updatedState.update {
                    it.copy(item = event.label)
                }
            }

            is LabelUpdatedEvent.OnResponseReceived -> {
                _updatedState.update {
                    it.copy(response = event.response)
                }
            }

            is LabelUpdatedEvent.OnLabelInfoShowed -> {
                _updatedState.update {
                    it.copy(isLabelInfoShowed = event.state)
                }
            }
        }
    }

    fun onLabelDeletedEvent(event: LabelDeletedEvent) {
        when (event) {
            is LabelDeletedEvent.OnLabelDeleted -> {
                _deletedState.update {
                    it.copy(id = event.id)
                }
            }

            is LabelDeletedEvent.OnResponseReceived -> {
                _deletedState.update {
                    it.copy(response = event.response)
                }
            }

            is LabelDeletedEvent.OnItemSelected -> {
                _deletedState.update {
                    it.copy(isItemDeleted = event.state)
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

            is LabelAddedEvent.OnResponseReceived -> {
                _addedState.update {
                    it.copy(response = event.response)
                }
            }

            is LabelAddedEvent.OnItemSelected -> {
                _addedState.update {
                    it.copy(isItemAdded = event.state)
                }
            }

            else -> {}
        }
    }


    /*
      Additional
   */

    data class LabelUpdatedState(
        var item: MapLabelDto? = null,
        var response: ServerResponse<Unit>? = null,
        var isLabelInfoShowed: Boolean = false
    )

    data class LabelAddedState(
        var item: MapLabelDto? = null,
        var response: ServerResponse<Unit>? = null,
        var isItemAdded: Boolean = false
    )

    data class LabelDeletedState(
        var id: String? = null,
        var response: ServerResponse<Unit>? = null,
        var isItemDeleted: Boolean = false
    )

    sealed class LabelUpdatedEvent {
        data class OnLabelUpdated(val label: MapLabelDto) : LabelUpdatedEvent()
        data class OnResponseReceived(val response: ServerResponse<Unit>) : LabelUpdatedEvent()
        data class OnLabelInfoShowed(val state: Boolean) : LabelUpdatedEvent()
    }

    sealed class LabelDeletedEvent {
        data class OnLabelDeleted(val id: String) : LabelDeletedEvent()
        data class OnResponseReceived(val response: ServerResponse<Unit>) : LabelDeletedEvent()
        data class OnItemSelected(val state: Boolean) : LabelDeletedEvent()
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

        data class OnResponseReceived(val response: ServerResponse<Unit>) : LabelAddedEvent()
        data class OnItemSelected(val state: Boolean) : LabelAddedEvent()
    }
}