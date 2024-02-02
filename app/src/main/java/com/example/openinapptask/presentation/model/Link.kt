package com.example.openinapptask.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Link(
    @SerialName("url_id")
    val urlId: Int,
    @SerialName("web_link")
    val webLink: String,
    @SerialName("smart_link")
    val smartLink: String,
    val title: String,
    @SerialName("total_clicks")
    val totalClicks: Int,
    @SerialName("original_image")
    val originalImage: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("domain_id")
    val domainId: String,
    @SerialName("url_suffix")
    val urlSuffix: String,
    val app: String,
    @SerialName("is_favourite")
    val isFavourite: Boolean
)