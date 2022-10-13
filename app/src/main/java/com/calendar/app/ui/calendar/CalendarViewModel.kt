package com.calendar.app.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calendar.app.model.Schedule
import com.calendar.app.network.ApiClient
import com.calendar.app.repository.calendar.CalendarAssetDataSource
import com.calendar.app.repository.calendar.CalendarRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(val calendarRepository: CalendarRepository, val calendarItemFragment: CalendarItemFragment) : ViewModel() {

    private val _schedules = MutableLiveData<List<Schedule>>()
    val schedules: LiveData<List<Schedule>> = _schedules
    val apiClient = ApiClient

    //    val calendarAssetDataSource = CalendarAssetDataSource(apiClient.create())

    suspend fun loadCalendarData(): List<Schedule> {
        val startDate = SimpleDateFormat("yyyy-MM", Locale.KOREA).format(calendarItemFragment.currentDate)
        return calendarRepository.getCalendarData(startDate)
    }
}