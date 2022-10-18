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

class CalendarViewModel(val calendarRepository: CalendarRepository) : ViewModel() {

    private val _schedules = MutableLiveData<ArrayList<Schedule>>()
    val schedules: LiveData<ArrayList<Schedule>> = _schedules

    fun getScheduleList(scheduleList: ArrayList<Schedule>) {
        _schedules.value = scheduleList
    }

    suspend fun loadCalendarData(calendarItemFragment: CalendarItemFragment): List<Schedule> {
        val startDate = SimpleDateFormat("yyyy-MM", Locale.KOREA).format(calendarItemFragment.currentDate)
        return calendarRepository.getCalendarData(startDate)
    }
}