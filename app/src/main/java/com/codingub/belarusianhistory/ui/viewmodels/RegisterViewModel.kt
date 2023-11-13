package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.RegisterUseCase
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.ui.auth.AuthState
import com.codingub.belarusianhistory.ui.auth.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val register: RegisterUseCase,
) : ViewModel() {

    val state = MutableLiveData(AuthState())
    private fun currentState() = state.value!!

    private val resultChannel = Channel<ServerResponse<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.SignUpLoginChanged -> {
                state.value = currentState().copy(signUpLogin = event.value)
            }

            is AuthUiEvent.SignUpUsernameChanged -> {
                state.value = currentState().copy(signUpUsername = event.value)
            }

            is AuthUiEvent.SignUpPasswordChanged -> {
                state.value = currentState().copy(signUpPassword = event.value)
            }

            is AuthUiEvent.SignUp -> {
                signUp()
            }

            else -> {}
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            resultChannel.send(ServerResponse.Loading(true))
            val result = register(
                login = currentState().signUpLogin,
                username = currentState().signUpUsername,
                password = currentState().signUpPassword,
                accessLevel = AccessLevel.User
            )
            resultChannel.send(result)
        }
    }
}