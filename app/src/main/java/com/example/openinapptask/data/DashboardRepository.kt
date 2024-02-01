package com.example.openinapptask.data

import com.example.openinapptask.data.model.DashboardApiModel
import com.example.openinapptask.data.model.LinkApiModel

interface DashboardRepository {

    suspend fun getDashboardData(): DashboardApiModel

    suspend fun getRecentLinksList(): List<LinkApiModel>
    suspend fun getTopLinksList(): List<LinkApiModel>
    suspend fun getFavouriteLinksList(): List<LinkApiModel>

}