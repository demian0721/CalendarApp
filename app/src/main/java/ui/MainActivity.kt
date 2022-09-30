package ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.calendar.app.R
import org.joda.time.DateTime
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd E HH:mm")
        val now: Long = DateTime().millis
        val today: Long = DateTime().withTimeAtStartOfDay().millis

        val nowString = simpleDateFormat.format(now)
        val todayString = simpleDateFormat.format(today)
        Log.d("asdf date",todayString)
        Log.d("asdf time",nowString)
    }
}