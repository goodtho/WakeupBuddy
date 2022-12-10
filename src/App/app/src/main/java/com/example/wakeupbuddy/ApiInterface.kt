package com.example.wakeupbuddy

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import java.com.example.wakeupbuddy.models.AlarmModel

interface ApiInterface {
    @GET("/alarms")
    suspend fun getAllAlarms(): Response<List<AlarmModel>>
}