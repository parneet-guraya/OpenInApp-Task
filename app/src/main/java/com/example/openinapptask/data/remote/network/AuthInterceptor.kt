package com.example.openinapptask.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

// TODO: Replace the dependency from String with some secure api (like Keystore) to fetch token
class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(AUTHORIZATION_HEADER, "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }
}