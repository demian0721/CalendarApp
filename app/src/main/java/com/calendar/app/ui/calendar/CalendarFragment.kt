package com.calendar.app.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.calendar.app.databinding.FragmentCalendarBinding
import com.calendar.app.ui.common.ViewModelFactory

class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by viewModels {
        ViewModelFactory()
    }

    private lateinit var binding: FragmentCalendarBinding

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
        val scheduleItemAdapter = ScheduleItemAdapter(viewModel)
        val lm = LinearLayoutManager(requireContext())

        binding.vpCalendar.adapter = calendarAdapter
        binding.vpCalendar.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.scheduleView.adapter = scheduleItemAdapter
        binding.scheduleView.layoutManager = lm
        calendarAdapter.apply {
            binding.vpCalendar.setCurrentItem(this.calendarFragmentPosition, false)
        }
        viewModel.schedules.observe(viewLifecycleOwner) {
            scheduleItemAdapter.submitList(it)
        }
    }
}
