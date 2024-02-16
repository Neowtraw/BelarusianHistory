package com.codingub.belarusianhistory.data.remote.network

import android.content.Context
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.utils.Constants.Injection.HISTORY_ENDPOINT
import com.codingub.belarusianhistory.utils.Constants.Injection.IS_DEBUG
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
open class HistoryNetworking @Inject constructor(
    @Named(IS_DEBUG) private val isDebugMode: Boolean,
    @Named(HISTORY_ENDPOINT) private val historyEndpoint: String,
    @ApplicationContext private val context: Context,
    private val appInterceptor: HistoryInterceptor,
    private val cacheInterceptor: CacheInterceptor
){
    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    // cache
    private val cacheSize = (10 * 1024 * 1024L) // 10 MB
    private val cache = Cache(context.cacheDir, cacheSize)

    private fun retrofit(): Retrofit {
        if(retrofit == null) retrofit = retrofitBuilder().build()
        return requireNotNull(retrofit)
    }

    @Synchronized
    open fun okHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .cache(cache)
                //.addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(appInterceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(
                        if (isDebugMode) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    )
                })

            okHttpClient = builder.build()
        }
        return requireNotNull(okHttpClient)
    }

    private fun retrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl("http://192.168.67.152:8080/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient())
    }

    fun historyAppApi(): HistoryAppApi = retrofit().create(HistoryAppApi::class.java)

}