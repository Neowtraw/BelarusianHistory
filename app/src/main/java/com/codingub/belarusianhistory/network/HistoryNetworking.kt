package com.codingub.belarusianhistory.network

import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.utils.Constants.Injection.HISTORY_ENDPOINT
import com.codingub.belarusianhistory.utils.Constants.Injection.IS_DEBUG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
open class HistoryNetworking @Inject constructor(
    @Named(IS_DEBUG) val isDebugMode: Boolean,
    @Named(HISTORY_ENDPOINT) private val historyEndpoint: String,
    private val interceptor: HistoryInterceptor
){
    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    private fun retrofit(): Retrofit {
        if(retrofit == null) retrofit = retrofitBuilder().build()
        return requireNotNull(retrofit)
    }

    @Synchronized
    open fun okHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(
                        if (isDebugMode) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    )
                }).addInterceptor(interceptor)

            okHttpClient = builder.build()
        }
        return requireNotNull(okHttpClient)
    }

    private fun retrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(historyEndpoint)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient())
    }

    fun historyAppApi(): HistoryAppApi = retrofit().create(HistoryAppApi::class.java)

}