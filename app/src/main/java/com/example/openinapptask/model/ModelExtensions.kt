package com.example.openinapptask.model

import com.example.openinapptask.data.model.DashboardApiModel
import com.example.openinapptask.data.model.DashboardDataApiModel
import com.example.openinapptask.data.model.LinkApiModel
import com.example.openinapptask.presentation.dashboard.DashboardDataScreenState
import com.example.openinapptask.presentation.dashboard.DashboardScreenState
import com.example.openinapptask.presentation.dashboard.LinkScreenState
import com.example.openinapptask.presentation.dashboard.LinksListType

fun DashboardApiModel.toUIModel(): Dashboard {
    return Dashboard(
        supportWhatsappNumber = this.supportWhatsappNumber,
        totalClicks = this.totalClicks,
        todayClicks = this.todayClicks,
        topSource = this.topSource,
        topLocation = this.topLocation,
        startTime = this.startTime,
        data = this.data.toUIModel()
    )
}

fun DashboardDataApiModel.toUIModel(): DashboardData {
    return DashboardData(
        recentLinks = this.recentLinks.map { it.toUIModel() },
        topLinks = this.topLinks.map { it.toUIModel() },
        favouriteLinks = this.favouriteLinks.map { it.toUIModel() },
        overallUrlChart = this.overallUrlChart
    )
}

fun LinkApiModel.toUIModel(): Link {
    return Link(
        urlId = urlId,
        webLink = webLink,
        smartLink = smartLink,
        title = title,
        totalClicks = totalClicks,
        originalImage = originalImage,
        createdAt = createdAt,
        domainId = domainId,
        urlSuffix = urlSuffix,
        app = app,
        isFavourite = isFavourite
    )
}

fun Dashboard.toState(): DashboardScreenState {
    return DashboardScreenState(
        isLoading = false,
        supportWhatsappNumber = this.supportWhatsappNumber,
        totalClicks = this.totalClicks,
        todayClicks = this.todayClicks,
        topSource = this.topSource,
        topLocation = this.topLocation,
    )
}

fun DashboardData.toState(): DashboardDataScreenState {
    return DashboardDataScreenState(
        isLoading = false,
        recentLinks = this.recentLinks.map { it.toState() },
        topLinks = this.topLinks.map { it.toState() },
        favouriteLinks = this.favouriteLinks.map { it.toState() },
        linksListType = LinksListType.TOP,
        overallUrlChart = this.overallUrlChart
    )

}

fun Link.toState(): LinkScreenState {
    return LinkScreenState(
        urlId = this.urlId,
        webLink = this.webLink,
        smartLink = this.smartLink,
        title = this.title,
        totalClicks = this.totalClicks,
        originalImage = this.originalImage,
        createdAt = this.createdAt,
        domainId = this.domainId,
        urlSuffix = this.urlSuffix,
        app = this.app,
        isFavourite = this.isFavourite
    )
}
