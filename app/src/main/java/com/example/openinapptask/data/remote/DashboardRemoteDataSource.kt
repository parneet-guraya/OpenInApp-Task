package com.example.openinapptask.data.remote

import com.example.openinapptask.data.model.DashboardApiModel

interface DashboardRemoteDataSource {
    suspend fun getDashboardData(): DashboardApiModel
}