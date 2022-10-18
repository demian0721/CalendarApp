package com.calendar.app.ui.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.calendar.app.AssetLoader
import com.calendar.app.R
import com.calendar.app.databinding.FragmentCalendarItemBinding
import com.calendar.app.model.Schedule
import com.calendar.app.network.ApiClient
import com.calendar.app.repository.calendar.CalendarAssetDataSource
import com.calendar.app.repository.calendar.CalendarRepository
import com.calendar.app.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class CalendarItemFragment : Fragment() {

    private val TAG = javaClass.simpleName
    lateinit var mContext: Context

    var pageIndex = 0

    lateinit var currentDate: Date
    lateinit var today: Date

    lateinit var binding: FragmentCalendarItemBinding
    val apiClient = ApiClient.create()

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: CalendarItemFragment? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mContext = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarItemBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageIndex -= (Int.MAX_VALUE / 2)
        Log.e(TAG, "Calendar Index: $pageIndex")

        val calendarAssetDataSource = CalendarAssetDataSource(apiClient)
        val calendarRepository = CalendarRepository(calendarAssetDataSource)
        val calendarViewModel = CalendarViewModel(calendarRepository)

        // 날짜 구하기
        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }
        val _today = Calendar.getInstance().run {
            time
        }

        currentDate = date
        Log.e(TAG, "$date")

        today = _today

        // 포맷 적용
        val datetime: String = SimpleDateFormat(
            mContext.getString(R.string.calendar_year_month_format),
            Locale.KOREA
        ).format(date.time)

        // 날짜 어댑터
        val calendarItemAdapter = CalendarItemAdapter(
            mContext,
            binding.calendarLayout,
            currentDate,
            today,
            calendarViewModel,
            this
        )
        binding.calendarYearMonthText.text = datetime
        binding.calendarView.adapter = calendarItemAdapter
        binding.calendarView.layoutManager =
            GridLayoutManager(mContext, 7, GridLayoutManager.VERTICAL, false)
        binding.calendarView.setHasFixedSize(true)
    }

    fun updateData() {
        activity?.runOnUiThread {
            binding.calendarView.adapter?.notifyDataSetChanged()
        }
    }
}