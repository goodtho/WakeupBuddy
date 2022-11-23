package com.example.wakeupbuddy

import android.content.Intent
import android.media.Ringtone
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
    }

    fun testOnClick(view: View) {
        println("Button clicked")

        val wkbApp = applicationContext as WakeUpBuddyApp
        val ringtone = wkbApp.getRingtone()
        ringtone.stop()

        val alarmManager = MyAlarmManager(this.applicationContext)
        alarmManager.stopAlarm()
        startActivity(Intent(this, HomeActivity::class.java))
    }

}