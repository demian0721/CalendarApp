package com.calendar.app.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.calendar.app.AssetLoader
import com.calendar.app.databinding.FragmentCalendarBinding
import com.calendar.app.repository.calendar.CalendarAssetDataSource
import com.calendar.app.repository.calendar.CalendarRepository

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarAdapter = CalendarAdapter(requireActivity())
        binding.vpCalendar.adapter = calendarAdapter
        binding.vpCalendar.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        calendarAdapter.apply {
            binding.vpCalendar.setCurrentItem(this.calendarFragmentPosition, false)
        }
    }
}