package com.calendar.app.repository.calendar

import com.calendar.app.AssetLoader
import com.calendar.app.model.Schedule
import com.google.gson.Gson
import com.calendar.app.model.ScheduleList
import com.calendar.app.network.ApiClient
import com.calendar.app.ui.calendar.CalendarItemAdapter
import com.calendar.app.ui.calendar.CalendarItemFragment
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class CalendarAssetDataSource(private val apiClient: ApiClient) : CalendarDataSource {

    private val gson = Gson()

    override suspend fun getCalendarData(startDate:String): List<Schedule> {
//        return assetLoader.getJsonString("10.json")?.let {
//            gson.fromJson(it, object : TypeToken<List<Schedule>>() {}.type)
//        }
        return apiClient.getCalendarData(startDate=startDate)
    }
}