package me.kevcar.scaffolding.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthKeyInterceptor(private val authKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithAuthKey = chain.request()
                .newBuilder()
                .addHeader("Api-Key", authKey)
                .build()
        return chain.proceed(requestWithAuthKey)
    }
}
