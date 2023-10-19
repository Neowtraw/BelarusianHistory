package com.codingub.belarusianhistory.ui.auth

sealed class AuthResult<T>(val data: T? = null){
    class Authorized<T>(data: T? = null): AuthResult<T>(data)
    class Unauthorized<T>: AuthResult<T>()
    class Conflict<T>(val errorMessage: String) : AuthResult<T>()
    class UnknownError<T>: AuthResult<T>()
}