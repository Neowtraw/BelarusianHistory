package com.codingub.belarusianhistory.data.remote.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

annotation class Cacheable

class CacheInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        request.tag(Invocation::class.java)?.let {
            if (!it.method().isAnnotationPresent(Cacheable::class.java)) {
                builder.cacheControl(CacheControl.Builder()
                    .noStore()
                    .build())
                return chain.proceed(builder.build())
            }
            try {
                builder.cacheControl(CacheControl.FORCE_NETWORK)
                return chain.proceed(builder.build())
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            builder.cacheControl(CacheControl.Builder()
                .maxAge(15, TimeUnit.MINUTES)
                .build())
        }
        return chain.proceed(builder.build())
    }
}

//class CacheInterceptor @Inject constructor() : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val response: Response = chain.proceed(chain.request())
//        val cacheControl: CacheControl = CacheControl.Builder()
//            .maxAge(15, TimeUnit.MINUTES) // 15 minutes cache
//            .build()
//        return response.newBuilder()
//            .removeHeader("Pragma")
//            .removeHeader("Cache-Control")
//            .header("Cache-Control", cacheControl.toString())
//            .build()
//    }
//}
