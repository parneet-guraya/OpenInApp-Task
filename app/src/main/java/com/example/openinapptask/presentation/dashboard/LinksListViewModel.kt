package com.example.openinapptask.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinapptask.BuildConfig
import com.example.openinapptask.data.DashboardRepository
import com.example.openinapptask.data.DashboardRepositoryImpl
import com.example.openinapptask.data.remote.DashboardRemoteDataSourceImpl
import com.example.openinapptask.data.remote.network.OpenInAppApi
import com.example.openinapptask.domain.GetLinksListUseCase
import com.example.openinapptask.domain.model.LinksListType
import com.example.openinapptask.model.Link
import com.example.openinapptask.model.Response
import com.example.openinapptask.model.toUIModel
import com.example.openinapptask.presentation.LinkListButtonTypes
import kotlinx.coroutines.launch

class LinksListViewModel : ViewModel() {
    private val dashboardRepository: DashboardRepository = DashboardRepositoryImpl(
        DashboardRemoteDataSourceImpl(
            OpenInAppApi.getOpenInAppApiService(
                BuildConfig.ACCESS_TOKEN
            )
        )
    )

    private var _listTabButton =
        MutableLiveData<LinkListButtonTypes>(LinkListButtonTypes.BUTTON_TOP_LIST)
    val listTabButton: LiveData<LinkListButtonTypes> = _listTabButton

    private var _linksList = MutableLiveData<Response<List<Link>>>()
    val linksList: LiveData<Response<List<Link>>> = _linksList

    fun onTabButtonClicked(buttonTypes: LinkListButtonTypes) {
        _listTabButton.value = buttonTypes
    }

    fun getLinksList(linksListType: LinksListType) {
        viewModelScope.launch {
            _linksList.value = Response.Loading
            try {
                val listResult =
                    GetLinksListUseCase(dashboardRepository).execute(linksListType)
                        .map { it.toUIModel() }
                _linksList.value = Response.Success(listResult)
            } catch (e: Exception) {
                //TODO: handle error
                _linksList.value = Response.Error("Error ${e.message ?: "Unknown"}")
            }
        }
    }

    fun buttonTypesToLinksListTypes(buttonTypes: LinkListButtonTypes): LinksListType {
        return when (buttonTypes) {
            LinkListButtonTypes.BUTTON_TOP_LIST -> LinksListType.TOP
            LinkListButtonTypes.BUTTON_RECENT_LIST -> LinksListType.RECENT
            LinkListButtonTypes.BUTTON_FAVOURITE_LIST -> LinksListType.FAVOURITE
        }
    }
}