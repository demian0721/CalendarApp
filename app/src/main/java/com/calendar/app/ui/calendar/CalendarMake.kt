package com.calendar.app.ui.calendar

import android.icu.text.SimpleDateFormat
import android.util.Log
import java.util.*

class CalendarMake(date: Date) {
    val TAG = "CalendarMake"

    companion object {
        const val DAYS_OF_WEEK = 7
        const val LOW_OF_CALENDAR = 6
    }

    val calendar = Calendar.getInstance()

    var prevTail = 0
    var nextHead = 0
    var currentMaxDate = 0

    var dateList = arrayListOf<Int>()
    var dateStrList = arrayListOf<String>()
    var weekList = arrayListOf<Int>()

    init {
        calendar.time = date
    }

    fun initBaseCalendar() {
        makeMonthDate()
    }

    private fun makeMonthDate() {

        dateList.clear() // 화면에 출력될 날짜 리스트
        dateStrList.clear() // 날짜 계산을 위한 년월일 포멧의 리스트
        calendar.set(Calendar.DATE, 1) // 해당 달의 1일로 캘린더 객체 세팅

        currentMaxDate = calendar.getActualMaximum(Calendar.DATE)

        prevTail =
            calendar.get(Calendar.DAY_OF_WEEK) - 1 // 캘린더 객체(해당 달의 1일)의 요일 -1 즉, 이전달 마지막 날짜의 요일
        Log.d(TAG, prevTail.toString())

        makePrevTail(calendar.clone() as Calendar) // 복제한 객체를 파라미터로 넘김
        makeCurrentMonth(calendar)

        nextHead =
            LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevTail + currentMaxDate) // 달력의 칸수 - (지난달 꼬리부분 + 이번 달 말일) = 다음달 머리부분
        makeNextHead(calendar.clone() as Calendar)
    }

    private fun makePrevTail(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1) // 복제한 캘린더를 이전달로 다시 세팅함
        Log.d(TAG, "calendar: ${calendar.toString()}") // 이전달 1일 캘린더 객체
        val maxDate = calendar.getActualMaximum(Calendar.DATE) // 이전달의 마지막 날짜
        Log.d(TAG, "maxDate: ${maxDate.toString()}")
        var maxOffsetDate = maxDate - prevTail // 현재달을 기준으로 달력에 보이지 않는 이전달 날짜가 몇일까지인지 구함
        Log.d(TAG, "maxOffsetDate: ${maxOffsetDate.toString()}")
        val newCalendar = calendar.clone() as Calendar // 년월일을 모두 표시할 새로운 캘린더 객체를 클론함 (이전달 객체에서 클론됨)
        Log.d(
            TAG,
            "formattedDate1: ${
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.KOREA
                ).format(newCalendar.time)
            }"
        ) // yyyy-MM-dd 형태로 포멧팅된 new 캘린더 객체 (1일)
//        newCalendar.add(Calendar.DATE, maxOffsetDate)
//        Log.d(TAG, "formattedDate2: ${SimpleDateFormat("yyyy-MM-dd",Locale.KOREA).format(newCalendar.time)}")
        for (i in 1..prevTail) { // i 는 prevTail 만큼 반복함
            dateList.add(++maxOffsetDate) // 표시될 날짜 리스트에 지난달 꼬리 날짜를 추가함 반복문으로 인해 maxOffsetDate 가 1씩 증가함
            newCalendar.set(Calendar.DATE, maxOffsetDate) // new 캘린더 객체의 날짜를 세팅함
            weekList.add(newCalendar.get(Calendar.DAY_OF_WEEK))
            dateStrList.add(
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.KOREA
                ).format(newCalendar.time)
            ) // 년월일 리스트에 꼬리날짜들을 추가함
        }
//        for (i in 1 .. prevTail) dateStrList.add()
// 2022-09-25 ... 2022-09-30
    }

    private fun makeCurrentMonth(calendar: Calendar) {
        val newCalendar = calendar.clone() as Calendar
        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) {
            newCalendar.set(Calendar.DATE, i)
            dateList.add(i)
            dateStrList.add(SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(newCalendar.time))
            weekList.add(newCalendar.get(Calendar.DAY_OF_WEEK))
        }
        Log.d(TAG, "currentMonthDate: ${dateList.toString()}")

    }

    private fun makeNextHead(calendar: Calendar) {
        var date = 1
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1) // 11월 1일
        for (i in 1..nextHead) {
            if (i != 1) {
                calendar.add(Calendar.DATE, 1)
            }
            dateList.add(date++)
            dateStrList.add(SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(calendar.time))
            weekList.add(calendar.get(Calendar.DAY_OF_WEEK))
        }
        Log.d(TAG, "dateStrList: ${dateStrList.toString()}")
        Log.d(TAG, "weekList: ${weekList.toString()}")
    }
}