package com.codingub.belarusianhistory.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.remote.network.DataUiResult
import com.codingub.belarusianhistory.data.remote.network.onServerError
import com.codingub.belarusianhistory.data.remote.network.onSuccess
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.domain.use_cases.AuthUseCase
import com.codingub.belarusianhistory.domain.use_cases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val authenticate: AuthUseCase
) : ViewModel() {

    val state = MutableLiveData(AuthState())
    private fun currentState() = state.value!!

    private val resultChannel = Channel<DataUiResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        auth()
    }

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.SignInLoginChanged -> {
                state.value = currentState().copy(signUpLogin = event.value)
            }

            is AuthUiEvent.SignInPasswordChanged -> {
                state.value = currentState().copy(signUpPassword = event.value)
            }

            is AuthUiEvent.SignIn -> {
                signIn()
            }

            else -> {}
        }
    }

    private fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            resultChannel.send(DataUiResult.Loading(true))
            val result = login(
                LoginRequest(
                    login = currentState().signInLogin,
                    password = currentState().signInPassword
                )
            ).onSuccess {
                resultChannel.send(DataUiResult.Success(this))
            }.onServerError {
                resultChannel.send(DataUiResult.Error(this))
            }
        }
    }

    private fun auth() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticate(Unit)
        }
    }

}