package com.codingub.belarusianhistory.ui.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.remote.network.DataUiResult
import com.codingub.belarusianhistory.data.remote.network.onServerError
import com.codingub.belarusianhistory.data.remote.network.onSuccess
import com.codingub.belarusianhistory.domain.use_cases.RoleUseCase
import com.codingub.belarusianhistory.sdk.AccessLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RoleViewModel @Inject constructor(
  private val roleUseCase: RoleUseCase
) : ViewModel() {

    private val role: MutableStateFlow<AccessLevel> = MutableStateFlow(AccessLevel.User)

    private val resultChannel = Channel<DataUiResult<String>>()
    val roleState = resultChannel.receiveAsFlow()

    fun changeRole(accessLevel: AccessLevel){
        role.value = accessLevel

        viewModelScope.launch(Dispatchers.IO) {

            resultChannel.send(DataUiResult.Loading(true))

            roleUseCase(role.value).onSuccess {
                resultChannel.send(DataUiResult.Success(this))
            }.onServerError {
                resultChannel.send(DataUiResult.Error(this))
            }
        }
    }
}