package com.calendar.app.network

import com.calendar.app.model.Schedule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("list.json")
    suspend fun getCalendarData(
        @Query("event_gubun") eventGubun: String = "PF",
        @Query("start_date") startDate: String = ""
    ): List<Schedule>

    companion object {
        fun create(): ApiClient {
            val baseUrl = "https://dgfc.or.kr/ajax/event/"

            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)
        }
    }
}