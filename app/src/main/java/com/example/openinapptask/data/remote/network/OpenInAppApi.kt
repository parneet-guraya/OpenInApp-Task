package com.example.openinapptask.data.remote.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


object OpenInAppApi {
    private const val BASE_URL = "https://api.inopenapp.com/api/v1/"
    private const val JSON_TYPE = "application/json"
    const val AUTH_TOKEN = ""

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(AUTH_TOKEN))
        .build()

    private val retrofitClient = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(JSON_TYPE.toMediaType()))
        .build()

    val openInAppApiService: OpenInAppApiService by lazy {
        retrofitClient.create(OpenInAppApiService::class.java)
    }
}