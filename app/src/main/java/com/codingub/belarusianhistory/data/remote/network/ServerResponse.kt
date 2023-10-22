package com.codingub.belarusianhistory.data.remote.network


sealed class ServerResponse<T>(val data: T? = null) {
    class OK<T>() : ServerResponse<T>()
    class Conflict<T>(val errorMessage: String) : ServerResponse<T>()
    class BadRequest<T>() : ServerResponse<T>()
    class Loading<T>(val state: Boolean = false) : ServerResponse<T>()
    class UnknownError<T>() : ServerResponse<T>()
}
