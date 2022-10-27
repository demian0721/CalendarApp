package com.calendar.app.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.calendar.app.network.ApiClient
import com.calendar.app.repository.calendar.CalendarAssetDataSource
import com.calendar.app.repository.calendar.CalendarRepository
import com.calendar.app.ui.calendar.CalendarViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CalendarViewModel::class.java) -> {
                val repository = CalendarRepository(CalendarAssetDataSource(ApiClient.create()))
                CalendarViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}