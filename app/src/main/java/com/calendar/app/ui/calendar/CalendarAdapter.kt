package com.calendar.app.ui.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CalendarAdapter(fragmentActivity: FragmentActivity, val viewModel:CalendarViewModel) : FragmentStateAdapter(fragmentActivity) {

//    val TAG = javaClass.simpleName
    private val pageCount = Int.MAX_VALUE
    val calendarFragmentPosition = Int.MAX_VALUE / 2

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val calendarItemFragment = CalendarItemFragment(viewModel)
        calendarItemFragment.pageIndex = position
        return calendarItemFragment
    }
}