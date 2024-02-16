package com.codingub.belarusianhistory.ui.viewmodels.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.local.prefs.UserConfig
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

    private val _isAddedAvailable = MutableStateFlow(false)
    val isAddedAvailable = _isAddedAvailable.asStateFlow()

    private val _isDeletedAvailable = MutableStateFlow(false)
    val isDeletedAvailable = _isDeletedAvailable.asStateFlow()

    private val _isUpdatedAvailable = MutableStateFlow(false)
    val isUpdatedAvailable = _isUpdatedAvailable.asStateFlow()

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

        val titleValid = addedState.value.title != null
        val descValid = addedState.value.description != null
        val hasError = listOf(titleValid, descValid).any { !it }

        if(hasError) {
            _addedState.update {
                it.copy(
                   titleError = titleValid,
                    descriptionError = descValid
                )
            }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            onLabelAddedEvent(
                LabelAddedEvent.OnResponseReceived(
                    addLabelOnMapUseCase(
                        MapLabelDto(
                            x = addedState.value.x!!,
                            y = addedState.value.y!!,
                            title = addedState.value.title!!,
                            description = addedState.value.description!!,
                            animation = addedState.value.animation,
                            image = addedState.value.image,
                            creatorLogin = UserConfig.getLogin(),
                            mapId = (map.value as ServerResponse.OK).value!!.id
                        )
                    )
                )
            )
        }

    }

    private fun deleteLabel() {
        require(isDeletedAvailable.value) {
            viewModelScope.launch(Dispatchers.IO) {
                onLabelDeletedEvent(
                    LabelDeletedEvent.OnResponseReceived(
                        deleteLabelFromMapUseCase(_deletedState.value.id ?: return@launch)
                    )
                )
            }
        }
    }

    private fun updateLabel() {
        require(isUpdatedAvailable.value) {
            viewModelScope.launch(Dispatchers.IO) {
                onLabelUpdatedEvent(
                    LabelUpdatedEvent.OnResponseReceived(
                        updateLabelOnMapUseCase(label = _updatedState.value.label ?: return@launch)
                    )
                )
            }
        }
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
                    it.copy(label = event.label)
                }
            }

            is LabelUpdatedEvent.OnResponseReceived -> {
                _updatedState.update {
                    it.copy(response = event.response)
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
        }
    }

    fun onLabelAddedEvent(event: LabelAddedEvent) {
        when (event) {
            is LabelAddedEvent.OnLabelInfoReset -> {
                _addedState.update {
                    it.copy(
                        title = null,
                        description = null,
                        x = null,
                        y = null,
                        image = null,
                        animation = null,
                        response = null
                    )
                }
            }

            is LabelAddedEvent.OnTitleSet -> {
                _addedState.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            is LabelAddedEvent.OnDescriptionSet -> {
                _addedState.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is LabelAddedEvent.OnSetCoordinates -> {
                _addedState.update {
                    it.copy(x = event.x, y = event.y)
                }
            }

            is LabelAddedEvent.OnSetImage -> {
                _addedState.update {
                    it.copy(image = event.image)
                }
            }

            is LabelAddedEvent.OnSetAnimation -> {
                _addedState.update {
                    it.copy(animation = event.animation)
                }
            }

            is LabelAddedEvent.OnResponseReceived -> {
                _addedState.update {
                    it.copy(response = event.response)
                }
            }

        }
    }

    fun changeAddedAvailable(state: Boolean) {
        _isAddedAvailable.value = state
    }

    fun changeUpdatedAvailable(state: Boolean) {
        _isUpdatedAvailable.value = state
    }

    fun changeDeletedAvailable(state: Boolean) {
        _isDeletedAvailable.value = state
    }

    /*
      Additional
   */

    data class LabelUpdatedState(
        val label: MapLabelDto? = null,
        var response: ServerResponse<Unit>? = null,
    )

    data class LabelAddedState(
        var title: String? = null,
        var titleError: Boolean = false,
        val description: String? = null,
        val descriptionError: Boolean = false,
        val x: Float? = null,
        val y: Float? = null,
        val image: String? = null,
        val animation: String? = null,
        var response: ServerResponse<Unit>? = null,
    )

    data class LabelDeletedState(
        var id: String? = null,
        var response: ServerResponse<Unit>? = null,
    )

    sealed class LabelUpdatedEvent {
        data class OnLabelUpdated(val label: MapLabelDto?) : LabelUpdatedEvent()
        data class OnResponseReceived(val response: ServerResponse<Unit>) : LabelUpdatedEvent()
    }

    sealed class LabelDeletedEvent {
        data class OnLabelDeleted(val id: String) : LabelDeletedEvent()
        data class OnResponseReceived(val response: ServerResponse<Unit>) : LabelDeletedEvent()
    }


    sealed class LabelAddedEvent {
        data object OnLabelInfoReset : LabelAddedEvent()
        data class OnTitleSet(val title: String?) : LabelAddedEvent()
        data class OnDescriptionSet(val description: String?) : LabelAddedEvent()
        data class OnSetCoordinates(val x: Float, val y: Float) : LabelAddedEvent()
        data class OnSetImage(val image: String?) : LabelAddedEvent()
        data class OnSetAnimation(val animation: String?) : LabelAddedEvent()
        data class OnResponseReceived(val response: ServerResponse<Unit>) : LabelAddedEvent()
    }
}