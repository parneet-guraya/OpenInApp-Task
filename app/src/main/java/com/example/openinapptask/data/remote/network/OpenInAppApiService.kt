package com.example.openinapptask.data.remote.network

import com.example.openinapptask.data.model.DashboardApiModel
import retrofit2.http.GET

interface OpenInAppApiService {

    @GET("dashboardNew")
    suspend fun getDashboardData(): DashboardApiModel
}