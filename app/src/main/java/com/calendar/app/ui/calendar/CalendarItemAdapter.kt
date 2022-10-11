package com.calendar.app.ui.calendar

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.calendar.app.R
import com.calendar.app.databinding.ItemCalendarViewBinding
import com.calendar.app.model.Schedule
import com.calendar.app.repository.calendar.CalendarRepository
import java.text.SimpleDateFormat
import java.util.*

class CalendarItemAdapter(
    val context: Context,
    val calendarLayout: LinearLayout,
    val date: Date,
    val today: Date,
    val events: List<Schedule>?
) :
    RecyclerView.Adapter<CalendarItemAdapter.CalendarItemViewHolder>() {

    private val TAG = javaClass.simpleName
    var dataList: ArrayList<Int> = arrayListOf()
    var dataStrList: ArrayList<String> = arrayListOf()
    var weekList: ArrayList<Int> = arrayListOf()

    /** CalendarViewModel 이용하여 날짜 리스트 만들기 */
    var calendarMake: CalendarMake = CalendarMake(date)

    init {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(date)
        calendarMake.initBaseCalendar()
        dataList = calendarMake.dateList
        dataStrList = calendarMake.dateStrList
        weekList = calendarMake.weekList
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemViewHolder {
        val binding =
            ItemCalendarViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarItemViewHolder, position: Int) {

        /** item_calendar_view 높이 지정 */
        val h = calendarLayout.height / 6
        holder.itemView.layoutParams.height = h
        holder.bind(dataList[position], position, context)
        if (itemClick != null) {
            val onClickListener = holder.itemView.setOnClickListener {
                itemClick?.onClick(it, position)
                holder.binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDate_Selected)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class CalendarItemViewHolder(var binding: ItemCalendarViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Int, position: Int, context: Context) {
            val firstDateIndex = calendarMake.prevTail
            val lastDateIndex = dataList.size - calendarMake.nextHead - 1

            /** 날짜 표시 */
            binding.itemCalendarDateText.text = data.toString()
            if(position in firstDateIndex until lastDateIndex && weekList[position]==1) {
                binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDay_Sun)
            }
            if(position in firstDateIndex until lastDateIndex && weekList[position]==7) {
                binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDay_Sat)
            }

            /** 오늘 날짜 처리 */
            val dateString: String = SimpleDateFormat("dd", Locale.KOREA).format(date)
            val dateInt = dateString.toInt()
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(today)
            if (dataList[position] == dateInt && dataStrList[position] == today) {
                binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDate_Today)
            }

            /** 스케쥴 있는 날짜 점찍기 */
            binding.itemCalendarEventDot.isVisible = false
            events?.run {
                for (i in 1 until events.size) {
                    if (dataStrList[position] == events[i].endDate) {
                        binding.itemCalendarEventDot.isVisible = true
                    }
                }
            }
            /** 이전달, 다음달 날짜 회색처리 */
            if (position < firstDateIndex || position > lastDateIndex) {
                binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDate_Dimmed)
            }
        }
    }
}