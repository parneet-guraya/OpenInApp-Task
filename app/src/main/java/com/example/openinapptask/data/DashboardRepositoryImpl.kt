package com.example.openinapptask.data

import com.example.openinapptask.data.model.DashboardApiModel
import com.example.openinapptask.data.model.LinkApiModel
import com.example.openinapptask.data.remote.DashboardRemoteDataSource

class DashboardRepositoryImpl(private val dashboardRemoteDataSource: DashboardRemoteDataSource) :
    DashboardRepository {
    override suspend fun getDashboardData(): DashboardApiModel =
        dashboardRemoteDataSource.getDashboardData()

    override suspend fun getRecentLinksList(): List<LinkApiModel> {
        return getDashboardData().data.recentLinks
    }

    override suspend fun getTopLinksList(): List<LinkApiModel> {
        return getDashboardData().data.topLinks
    }

    override suspend fun getFavouriteLinksList(): List<LinkApiModel> {
        return getDashboardData().data.favouriteLinks
    }
}