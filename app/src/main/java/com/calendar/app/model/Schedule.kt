package com.calendar.app.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Schedule(
    @SerializedName("end_date") val endDate: String,
    @SerializedName("pay_gubun_name") val payText: String,
    val subject: String,
    val place: String,
    @SerializedName("event_gubun_name") val eventText: String,
)
