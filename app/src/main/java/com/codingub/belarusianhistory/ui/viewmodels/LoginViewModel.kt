package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.AuthUseCase
import com.codingub.belarusianhistory.domain.use_cases.LoginUseCase
import com.codingub.belarusianhistory.ui.auth.AuthState
import com.codingub.belarusianhistory.ui.auth.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase
) : ViewModel() {

    val state = MutableStateFlow(AuthState())

    private val resultChannel = Channel<ServerResponse<Unit>>()
    val authResults = resultChannel.receiveAsFlow()


    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.SignInLoginChanged -> {
                state.value = state.value.copy(signInLogin = event.value)
            }

            is AuthUiEvent.SignInPasswordChanged -> {
                state.value = state.value.copy(signInPassword = event.value)
            }

            is AuthUiEvent.SignIn -> {
                signIn()
            }

            else -> {}
        }
    }

    private fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            resultChannel.send(ServerResponse.Loading(true))
            val result = login(
                login = state.value.signInLogin,
                password = state.value.signInPassword
            )
            resultChannel.send(result)
        }
    }
}