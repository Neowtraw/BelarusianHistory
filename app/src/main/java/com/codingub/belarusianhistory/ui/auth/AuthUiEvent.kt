package com.codingub.belarusianhistory.ui.auth

sealed class AuthUiEvent {

    data class SignUpLoginChanged(val value: String): AuthUiEvent()
    data class SignUpUsernameChanged(val value: String): AuthUiEvent()
    data class SignUpPasswordChanged(val value: String): AuthUiEvent()
    object SignUp: AuthUiEvent()


    data class SignInLoginChanged(val value: String): AuthUiEvent()
    data class SignInPasswordChanged(val value: String): AuthUiEvent()
    object SignIn: AuthUiEvent()
}