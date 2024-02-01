package com.example.openinapptask.presentation.dashboard

// UI state for the Dashboard screen
data class DashboardScreenState(
    val isLoading: Boolean,
    val supportWhatsappNumber: String,
    val totalClicks: Int,
    val todayClicks: Int,
    val topSource: String,
    val topLocation: String
)

// UI state for the DashboardData
data class DashboardDataScreenState(
    val isLoading: Boolean,
    val recentLinks: List<LinkScreenState>,
    val topLinks: List<LinkScreenState>,
    val favouriteLinks: List<LinkScreenState>,
    val linksListType: LinksListType,
    val overallUrlChart: Map<String, Int>
)

// UI state for a Link
data class LinkScreenState(
    val urlId: Int,
    val webLink: String,
    val smartLink: String,
    val title: String,
    val totalClicks: Int,
    val originalImage: String,
    val createdAt: String,
    val domainId: String,
    val urlSuffix: String,
    val app: String,
    val isFavourite: Boolean
)

enum class LinksListType{
    RECENT,
    TOP,
    FAVOURITE,
}