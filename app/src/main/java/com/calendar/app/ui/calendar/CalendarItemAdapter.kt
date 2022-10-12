package com.calendar.app.ui.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.calendar.app.R
import com.calendar.app.databinding.ItemCalendarViewBinding
import com.calendar.app.model.Schedule
import java.text.SimpleDateFormat
import java.util.*

class CalendarItemAdapter(
    private val context: Context,
    private val calendarLayout: LinearLayout,
    date: Date,
    today: Date,
    val events: List<Schedule>?
) :
    RecyclerView.Adapter<CalendarItemAdapter.CalendarItemViewHolder>() {

    /** CalendarViewModel 이용하여 날짜 리스트 만들기 */
    var calendarMake: CalendarMake = CalendarMake(date)

//    private val TAG = javaClass.simpleName
    var dataList: ArrayList<Int> = arrayListOf()
    var dataStrList: ArrayList<String> = arrayListOf()
    var weekList: ArrayList<Int> = arrayListOf()
    private var selectedDate: String = ""
    private val today: String = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(today)

    init {
        calendarMake.initBaseCalendar()
        dataList = calendarMake.dateList
        dataStrList = calendarMake.dateStrList
        weekList = calendarMake.weekList
    }

    private var previousHolder: CalendarItemViewHolder? = null
    val firstDateIndex = calendarMake.prevTail
    val lastDateIndex = dataList.size - calendarMake.nextHead - 1

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

        if (position in firstDateIndex..lastDateIndex) {
            holder.binding.root.setOnClickListener {
//            Log.d(TAG, "previousHolder: $previousHolder")
                if (previousHolder != null) {
                    prevDateStateChange(previousHolder!!)
                }
                selectedDate = dataStrList[position]
                dateStateChange(position, holder)
                previousHolder = holder
            }
        }

    }

    /** 선택된 날짜 처리 */
    private fun dateStateChange(position: Int, holder: CalendarItemViewHolder) {
//        Log.d(TAG, "currentPosition:${position.toString()}-${dataStrList[position]}")
        if (dataStrList[position] == selectedDate) {
            holder.binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDate_Selected)
        }

    }

    /** 전에 선택된 날짜 처리 */
//        Log.d(TAG, "previousPosition:${holder.position}-${dataStrList[holder.position]}:${weekList[holder.position]}")
    private fun prevDateStateChange(holder: CalendarItemViewHolder) {
        when {
            weekList[holder.adapterPosition] == 1 -> holder.binding.itemCalendarDateText.setTextAppearance(
                R.style.CalendarDay_Sun
            )
            weekList[holder.adapterPosition] == 7 -> holder.binding.itemCalendarDateText.setTextAppearance(
                R.style.CalendarDay_Sat
            )
            dataStrList[holder.adapterPosition] == today -> holder.binding.itemCalendarDateText.setTextAppearance(
                R.style.CalendarDate_Today
            )
            else -> holder.binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDate)
        }
    }


    override fun getItemCount(): Int = dataList.size

    inner class CalendarItemViewHolder(var binding: ItemCalendarViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Int, position: Int, context: Context) {

            /** 날짜 표시 */
            binding.itemCalendarDateText.text = data.toString()
            if (position in firstDateIndex until lastDateIndex && weekList[position] == 1) {
                binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDay_Sun)
            }
            if (position in firstDateIndex until lastDateIndex && weekList[position] == 7) {
                binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDay_Sat)
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

            /** 오늘 날짜 처리*/
            if (dataStrList[position] == today) {
                binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDate_Today)
            }

            /** 이전달, 다음달 날짜 회색처리 */
            if (position < firstDateIndex || position > lastDateIndex) {
                binding.itemCalendarDateText.setTextAppearance(R.style.CalendarDate_Dimmed)
            }
        }
    }
}