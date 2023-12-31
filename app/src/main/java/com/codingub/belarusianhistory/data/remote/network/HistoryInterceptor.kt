package com.codingub.belarusianhistory.data.remote.network

import com.codingub.belarusianhistory.data.local.prefs.UserConfig
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import javax.inject.Inject

class HistoryInterceptor @Inject constructor() : Interceptor {

    private val tryCnt = 3
    private val baseInterval = 1500L

    override fun intercept(chain: Interceptor.Chain): Response {
        return process(chain, attempt = 1)
    }

    private fun process(chain: Interceptor.Chain, attempt: Int): Response {
        var response: Response? = null
        try {
            val request = chain.request().newBuilder()
            request.addHeader("Authorization", UserConfig.getToken())

            response = chain.proceed(request.build())
            if (attempt < tryCnt && !response.isSuccessful) {
                return delayedAttempt(chain, response, attempt)
            }
            return response
        } catch (e: Exception) {
            if (attempt < tryCnt && networkRetryCheck(e)) {
                return delayedAttempt(chain, response, attempt)
            }
            throw e
        }
    }

    private fun delayedAttempt(
        chain: Interceptor.Chain,
        response: Response?,
        attempt: Int,
    ): Response {
        response?.body?.close()
        Thread.sleep(baseInterval * attempt)
        return process(chain, attempt = attempt + 1)
    }
}

private val networkRetryCheck: (Throwable) -> Boolean = {
    val shouldRetry = when {
        it.isHttp4xx() -> false
        else -> true
    }
    shouldRetry
}

fun Throwable.isHttp4xx(): Boolean {
    if (this is HttpException) {
        val responseCode = this.code()
        return responseCode in 400..499
    }
    return false
}