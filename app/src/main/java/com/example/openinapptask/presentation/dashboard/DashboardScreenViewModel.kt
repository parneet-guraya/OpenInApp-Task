package com.example.openinapptask.presentation.dashboard

import android.icu.util.Calendar
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
import com.example.openinapptask.model.Dashboard
import com.example.openinapptask.model.Link
import com.example.openinapptask.model.Response
import com.example.openinapptask.model.toUIModel
import com.example.openinapptask.presentation.DashboardButtonTypes
import kotlinx.coroutines.launch

class DashboardScreenViewModel() : ViewModel() {

    private var _screenData = MutableLiveData<Response<Dashboard>>()
    val screenData: LiveData<Response<Dashboard>> = _screenData

    private var _linksList = MutableLiveData<Response<List<Link>>>()
    val linksList: LiveData<Response<List<Link>>> = _linksList

    private var _listTabButton = MutableLiveData(DashboardButtonTypes.BUTTON_TOP_LIST)
    val listTabButton: LiveData<DashboardButtonTypes> = _listTabButton

    var greetingLiveData = MutableLiveData<String>()

    private val dashboardRepository: DashboardRepository = DashboardRepositoryImpl(
        DashboardRemoteDataSourceImpl(
            OpenInAppApi.getOpenInAppApiService(
                BuildConfig.ACCESS_TOKEN
            )
        )
    )

    fun setGreetingBasedOnTime() {
        // Logic for setting greeting based on time
        val calendar: Calendar = Calendar.getInstance()
        val hourOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)
        if (hourOfDay >= 0 && hourOfDay < 12) {
            greetingLiveData.setValue("Good Morning!")
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            greetingLiveData.setValue("Good Afternoon!")
        } else {
            greetingLiveData.setValue("Good Evening!")
        }
    }

    fun getScreenData() {
        viewModelScope.launch {
            _screenData.value = Response.Loading
            try {
                val dashboardScreenResult = dashboardRepository.getDashboardData().toUIModel()
                _screenData.value = Response.Success(dashboardScreenResult)
            } catch (e: Exception) {
                // TODO: handle
                _screenData.value = Response.Error("Error ${e.message ?: "Unknown"}")
            }
        }
    }

    fun onTabButtonClicked(buttonTypes: DashboardButtonTypes){
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

    fun buttonTypesToLinksListTypes(buttonTypes: DashboardButtonTypes): LinksListType {
       return when(buttonTypes){
            DashboardButtonTypes.BUTTON_TOP_LIST -> LinksListType.TOP
            DashboardButtonTypes.BUTTON_RECENT_LIST -> LinksListType.RECENT
        }
    }
}