package com.codingub.belarusianhistory.network

import com.codingub.belarusianhistory.utils.Constants.Injection.BUILD_VERSION_CODE
import com.codingub.belarusianhistory.utils.Constants.Injection.BUILD_VERSION_NAME
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class HistoryInterceptor @Inject constructor(
    //user token +
    @Named(BUILD_VERSION_CODE) private val buildVersionCode: Int,
    @Named(BUILD_VERSION_NAME) private val buildVersionName: String
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val newChain = chain.request().newBuilder()

        newChain.addHeader("appVersion", buildVersionCode.toString())
            .addHeader("appVersionName", buildVersionName)
            .addHeader("source", "android")
        return chain.proceed(newChain.build())
    }
}