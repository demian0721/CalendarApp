package ui.calendar

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.calendar.app.databinding.ItemCalendarViewBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarItemAdapter(val context: Context, val calendarLayout: LinearLayout, val date: Date) :
    RecyclerView.Adapter<CalendarItemAdapter.CalendarItemViewHolder>() {

    private val TAG = javaClass.simpleName
    var dataList: ArrayList<Int> = arrayListOf()

    /** CalendarViewModel 이용하여 날짜 리스트 만들기 */
    var calendarViewModel: CalendarViewModel = CalendarViewModel(date)

    init {
        calendarViewModel.initBaseCalendar()
        dataList = calendarViewModel.dateList
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
//        Log.d(TAG, dataList.toString())
        holder.bind(dataList[position], position, context)
        if (itemClick != null) {
            val onClickListener = holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)

            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class CalendarItemViewHolder(var binding: ItemCalendarViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Int, position: Int, context: Context) {
//            Log.d(TAG, "${customCalendar.prevTail}, ${customCalendar.nextHead}")
            val firstDateIndex = calendarViewModel.prevTail
            val lastDateIndex = dataList.size - calendarViewModel.nextHead - 1
//            Log.d(TAG, "$firstDateIndex, $lastDateIndex")

            /** 날짜 표시 */
            Log.e(TAG, data.toString())
            binding.itemCalendarDateText.text = data.toString()
            /** 오늘 날짜 처리 */
            val dateString: String = SimpleDateFormat("dd", Locale.KOREA).format(date)
            val dateInt = dateString.toInt()
            if (dataList[position] == dateInt) {
                binding.itemCalendarDateText.setTypeface(
                    binding.itemCalendarDateText.typeface,
                    Typeface.BOLD
                )
            }

            if (position < firstDateIndex || position > lastDateIndex) {
                // TODO 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 회색처리
            }
        }
    }
}