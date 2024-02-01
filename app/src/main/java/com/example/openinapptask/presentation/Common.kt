package com.example.openinapptask.presentation

import com.example.openinapptask.domain.model.LinksListType

enum class DashboardButtonTypes {
    BUTTON_TOP_LIST,
    BUTTON_RECENT_LIST
}

enum class LinkListButtonTypes {
    BUTTON_TOP_LIST,
    BUTTON_RECENT_LIST,
    BUTTON_FAVOURITE_LIST,
}

fun LinksListType.toLinkListButtonTypes(): LinkListButtonTypes {
    return when (this) {
        LinksListType.RECENT -> LinkListButtonTypes.BUTTON_RECENT_LIST
        LinksListType.TOP -> LinkListButtonTypes.BUTTON_TOP_LIST
        LinksListType.FAVOURITE -> LinkListButtonTypes.BUTTON_FAVOURITE_LIST
    }
}