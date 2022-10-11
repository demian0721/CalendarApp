package com.calendar.app.repository.calendar

import com.calendar.app.AssetLoader
import com.calendar.app.model.Schedule
import com.google.gson.Gson
import com.calendar.app.model.ScheduleList
import com.google.gson.reflect.TypeToken

class CalendarAssetDataSource(private val assetLoader: AssetLoader) : CalendarDataSource {

    private val gson = Gson()

    override fun getCalendarData(): List<Schedule>? {
        return assetLoader.getJsonString("10.json")?.let {
            gson.fromJson(it, object : TypeToken<List<Schedule>>() {}.type)
        }
    }
}