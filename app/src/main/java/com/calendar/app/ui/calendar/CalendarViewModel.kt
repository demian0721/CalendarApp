package com.calendar.app.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.calendar.app.model.Schedule
import com.calendar.app.repository.calendar.CalendarRepository

class CalendarViewModel(val calendarRepository: CalendarRepository) : ViewModel() {

    private val _schedules = MutableLiveData<List<Schedule>>()
    val schedules: LiveData<List<Schedule>> = _schedules

    init {
        loadCalendarData()
    }

    private fun loadCalendarData() {
        val calendarData = calendarRepository.getCalendarData()
        calendarData?.let {
            _schedules.value = it
        }
    }
}