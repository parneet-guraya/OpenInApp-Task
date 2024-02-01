package com.example.openinapptask.data.remote

import com.example.openinapptask.data.model.DashboardApiModel
import com.example.openinapptask.data.remote.network.OpenInAppApiService

class DashboardRemoteDataSourceImpl(private val openInAppApiService: OpenInAppApiService) :
    DashboardRemoteDataSource {
    override suspend fun getDashboardData(): DashboardApiModel {
        return openInAppApiService.getDashboardData()
    }

}