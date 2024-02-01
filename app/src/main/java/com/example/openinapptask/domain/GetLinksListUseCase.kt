package com.example.openinapptask.domain

import com.example.openinapptask.data.DashboardRepository
import com.example.openinapptask.data.model.LinkApiModel
import com.example.openinapptask.domain.model.LinksListType


class GetLinksListUseCase(private val repository: DashboardRepository) {
    suspend fun execute(linksListType: LinksListType): List<LinkApiModel> {
        return when (
            linksListType) {
            LinksListType.RECENT -> {
                repository.getRecentLinksList()
            }

            LinksListType.TOP -> {
                repository.getTopLinksList()
            }

            LinksListType.FAVOURITE -> {
                emptyList<LinkApiModel>()
            }
        }
    }
}