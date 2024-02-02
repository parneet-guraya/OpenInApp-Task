package com.example.openinapptask.model

data class Dashboard(
    val supportWhatsappNumber: String,
    val totalClicks: Int,
    val todayClicks: Int,
    val topSource: String,
    val topLocation: String,
    val startTime: String,
    val data: DashboardData
)

data class DashboardData(
    val recentLinks: List<Link>,
    val topLinks: List<Link>,
    val favouriteLinks: List<Link>,
    val overallUrlChart: Map<String, Int>
)


