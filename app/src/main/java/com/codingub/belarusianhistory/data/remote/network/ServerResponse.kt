package com.codingub.belarusianhistory.data.remote.network

data class ServerResponse<T>(
    var data: T,
    var message: String = "",
    var status: Int
)

fun <T> ServerResponse<T>.dataOrThrowException(executeOnSuccess: T.() -> Unit = {}) : T {
    return if (isSuccess()) data.also(executeOnSuccess) else throw serverException()
}

suspend fun <T> ServerResponse<T>.suspendDataOrThrowException(
    executeOnSuccess: suspend T.() -> Unit = {}
) : T{
    return if (isSuccess()) data.also { executeOnSuccess(it) }
    else throw serverException()
}

suspend fun <T, E> ServerResponse<T>.mapDataOrThrowException(
    mapper: suspend (T) -> E, executeOnSuccess: suspend T.() -> Unit = {}
): E {
    return if (isSuccess()) {
        executeOnSuccess(data)
        mapper(data)
    } else throw serverException()
}


fun <T> ServerResponse<T>.isSuccess() = status == 200
fun <T> ServerResponse<T>.serverException() = ServerException(status, message)