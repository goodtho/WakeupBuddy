package com.example.wakeupbuddy

import android.app.Application
import android.content.Context

class WakeUpBuddyApp : Application() {
    private var myAMInitalized: Boolean = false
    private lateinit var myAM: MyAlarmManager

    fun initializeMyAlarmManager(context: Context) {
        myAMInitalized = true
        myAM = MyAlarmManager(context)
    }

    fun getMyAlarmManager(): MyAlarmManager = myAM

    fun myAlarmManagerIsInitialized(): Boolean = myAMInitalized
}