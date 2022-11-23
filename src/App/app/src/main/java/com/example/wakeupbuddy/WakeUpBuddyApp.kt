package com.example.wakeupbuddy

import android.app.Application
import android.content.Context

class WakeUpBuddyApp : Application() {
    private lateinit var myAM: MyAlarmManager

    fun initializeMyAlarmManager(context: Context) {
        myAM = MyAlarmManager(context)
    }

    fun getMyAlarmManager(): MyAlarmManager = myAM
}