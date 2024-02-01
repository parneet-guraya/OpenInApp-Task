package com.example.openinapptask.data.remote.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


object OpenInAppApi {
    private const val BASE_URL = "https://api.inopenapp.com/api/v1/"
    private const val JSON_TYPE = "application/json"

    fun getOpenInAppApiService(token: String): OpenInAppApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        val retrofitClient = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(JSON_TYPE.toMediaType()))
            .build()
       return retrofitClient.create(OpenInAppApiService::class.java)
    }
}