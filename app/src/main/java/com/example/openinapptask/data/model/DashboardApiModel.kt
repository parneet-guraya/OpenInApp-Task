package com.example.openinapptask.data.model

import com.example.openinapptask.data.model.LinkApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashboardApiModel(
    val status: Boolean,
    @SerialName("statusCode")
    val statusCode: Int,
    val message: String,
    @SerialName("support_whatsapp_number")
    val supportWhatsappNumber: String,
    @SerialName("extra_income")
    val extraIncome: Double,
    @SerialName("total_links")
    val totalLinks: Int,
    @SerialName("total_clicks")
    val totalClicks: Int,
    @SerialName("today_clicks")
    val todayClicks: Int,
    @SerialName("top_source")
    val topSource: String,
    @SerialName("top_location")
    val topLocation: String,
    val startTime: String,
    @SerialName("links_created_today")
    val linksCreatedToday: Int,
    @SerialName("applied_campaign")
    val appliedCampaign: Int,
    val data: DashboardDataApiModel
)

@Serializable
data class DashboardDataApiModel(
    @SerialName("recent_links")
    val recentLinks: List<LinkApiModel>,
    @SerialName("top_links")
    val topLinks: List<LinkApiModel>,
    @SerialName("favourite_links")
    val favouriteLinks: List<LinkApiModel>,
    @SerialName("overall_url_chart")
    val overallUrlChart: Map<String, Int>
)