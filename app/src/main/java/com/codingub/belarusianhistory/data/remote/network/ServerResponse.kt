package com.codingub.belarusianhistory.data.remote.network


sealed class ServerResponse<T>(val data: T? = null) {
    class OK<T>(val value: T? = null) : ServerResponse<T>()
    class Loading<T>(val state: Boolean = false) : ServerResponse<T>()
    class Error<T>(val errorMessage: String) : ServerResponse<T>()

    //auth
    class Authorized<T>(data: T? = null): ServerResponse<T>(data)
    class Unauthorized<T>: ServerResponse<T>()
}
