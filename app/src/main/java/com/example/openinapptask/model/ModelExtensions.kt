package com.example.openinapptask.model

import com.example.openinapptask.data.model.DashboardApiModel
import com.example.openinapptask.data.model.DashboardDataApiModel
import com.example.openinapptask.data.model.LinkApiModel

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