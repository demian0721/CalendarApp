package com.calendar.app.ui.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.calendar.app.model.Schedule
import com.calendar.app.repository.calendar.CalendarRepository
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(
    private val calendarRepository: CalendarRepository
) : ViewModel() {

    private val _schedules = MutableLiveData<ArrayList<Schedule>>()
    val schedules: LiveData<ArrayList<Schedule>> = _schedules

    fun setScheduleList(scheduleList: ArrayList<Schedule>) {
        _schedules.value = scheduleList
        Log.d("asdf", "getScheduleList: ${scheduleList.toString()}")
        Log.d("asdf", "schedules: ${schedules.value}")
    }

    suspend fun loadCalendarData(calendarItemFragment: CalendarItemFragment): List<Schedule> {
        val startDate =
            SimpleDateFormat("yyyy-MM", Locale.KOREA).format(calendarItemFragment.currentDate)
        return calendarRepository.getCalendarData(startDate)
    }
}