package com.example.wakeupbuddy

import android.app.Application
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import java.util.*


class WakeUpBuddyApp : Application() {
    private var myAMInitalized: Boolean = false
    private lateinit var myAM: MyAlarmManager
    private lateinit var mySettings: Settings

    fun initializeMyAlarmManager(context: Context) {

        //todo get user settings from the db and initialize global (wkbApp) settings object

        val prefs = getSharedPreferences("UserInfo", 0)
        val uri = Uri.parse(prefs.getString("AlarmToneUri", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString()))
        val ringtone = RingtoneManager.getRingtone(applicationContext, uri)
        mySettings = Settings(prefs.getBoolean("Vibration", true), TimeZone.getDefault(), ringtone)

        myAMInitalized = true
        myAM = MyAlarmManager(context)
    }

    fun getMyAlarmManager(): MyAlarmManager = myAM

    fun myAlarmManagerIsInitialized(): Boolean = myAMInitalized

    fun getRingtone() : Ringtone {
        return mySettings.ringtone
    }

    fun setRingtone(uri: Uri) {
        mySettings.ringtone = RingtoneManager.getRingtone(applicationContext, uri)
        val settings = getSharedPreferences("UserInfo", 0)
        val editor = settings.edit()
        editor.putString("AlarmToneUri", uri.toString())
        editor.apply()
    }

}