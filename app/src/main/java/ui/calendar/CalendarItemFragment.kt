package ui.calendar

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.calendar.app.R
import com.calendar.app.databinding.FragmentCalendarItemBinding
import ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class CalendarItemFragment : Fragment() {

    private val TAG = javaClass.simpleName
    lateinit var mContext: Context


    var pageIndex = 0

    lateinit var currentDate: Date
    lateinit var binding: FragmentCalendarItemBinding

    companion object {
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
        binding = FragmentCalendarItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageIndex -= (Int.MAX_VALUE / 2)
        Log.e(TAG, "Calendar Index: $pageIndex")
        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }
        currentDate = date
        Log.e(TAG, "$date")
    }
}