package com.calendar.app.repository.calendar

import com.calendar.app.model.Schedule
import com.calendar.app.model.ScheduleList

class CalendarRepository(private val assetDataSource: CalendarAssetDataSource) {
    suspend fun getCalendarData(startDate: String): List<Schedule> {
        return assetDataSource.getCalendarData(startDate)
    }
}