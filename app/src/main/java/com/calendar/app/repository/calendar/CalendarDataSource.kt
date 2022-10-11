package com.calendar.app.repository.calendar

import com.calendar.app.model.Schedule
import com.calendar.app.model.ScheduleList

interface CalendarDataSource {
    fun getCalendarData() : List<Schedule>?
}