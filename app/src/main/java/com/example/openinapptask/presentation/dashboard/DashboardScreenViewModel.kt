package com.example.openinapptask.presentation.dashboard

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.openinapptask.data.DashboardRepository
import com.example.openinapptask.model.Dashboard
import com.example.openinapptask.model.Link
import com.example.openinapptask.model.Response
import com.example.openinapptask.model.toUIModel
import kotlinx.coroutines.launch

class DashboardScreenViewModel(private val dashboardRepository: DashboardRepository) : ViewModel() {

    private var _screenData = MutableLiveData<Response<Dashboard>>()
    val screenData: LiveData<Response<Dashboard>> = _screenData

    private var _linksList = MutableLiveData<Response<List<Link>>>()
    val linksList: LiveData<Response<List<Link>>> = _linksList

    private var _listTabButton = MutableLiveData<LinksListType>(LinksListType.TOP)
    val listTabButton: LiveData<LinksListType> = _listTabButton

    var greetingLiveData = MutableLiveData<String>()

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

    //    init {
//        getData()
//        getLinksList()
//    }
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

    private fun getRecentLinksList() {
        viewModelScope.launch {
            _linksList.value = Response.Loading
            try {
                val listResult =
                    dashboardRepository.getRecentLinksList().map { it.toUIModel() }
                _linksList.value = Response.Success(listResult)
            } catch (e: Exception) {
                //TODO: handle error
                _linksList.value = Response.Error("Error ${e.message ?: "Unknown"}")

            }
        }
    }

    private fun getTopLinksList() {
        viewModelScope.launch {
            _linksList.value = Response.Loading
            try {
                val listResult =
                    dashboardRepository.getTopLinksList().map { it.toUIModel() }
                _linksList.value = Response.Success(listResult)
            } catch (e: Exception) {
                //TODO: handle error
                _linksList.value = Response.Error("Error ${e.message ?: "Unknown"}")
            }
        }
    }

    fun getLinksList(linksListType: LinksListType) {
        _listTabButton.value = linksListType
        when (linksListType) {
            LinksListType.RECENT -> {
                getRecentLinksList()
            }

            LinksListType.TOP -> getTopLinksList()
            LinksListType.FAVOURITE -> {// get favourite list
            }
        }
    }


    companion object {
        class DashboardViewModelFactory(private val dashboardRepository: DashboardRepository) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DashboardScreenViewModel(dashboardRepository) as T
            }
        }
    }
}