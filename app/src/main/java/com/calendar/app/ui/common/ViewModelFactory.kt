package com.calendar.app.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.calendar.app.ui.calendar.CalendarMake
import java.util.*

class ViewModelFactory(private val context:Context, val date: Date):ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CalendarMake::class.java) -> {
                val calendar = Calendar.getInstance()
                calendar.time = date
                CalendarMake(date) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}