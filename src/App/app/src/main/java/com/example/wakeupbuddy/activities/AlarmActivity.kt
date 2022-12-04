package com.example.wakeupbuddy.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.databinding.ActivityAlarmBinding
import java.util.*

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val curDate = Calendar.getInstance()
        binding.alarmTime.text =
            "${curDate.get(Calendar.HOUR_OF_DAY)}:${curDate.get(Calendar.MINUTE)}"
        binding.alarmDate.text =
            "${getWeekdayName(curDate.get(Calendar.DAY_OF_WEEK))}, ${curDate.get(Calendar.DAY_OF_MONTH)}. ${
                getMonthName(curDate.get(Calendar.MONTH))
            }"

        binding.deactivateAlarmButton.setOnClickListener { view: View ->
            println("Stop button clicked")

            val wkbApp = applicationContext as WakeUpBuddyApp
            val alarmManager = wkbApp.getMyAlarmManager()

            val extras = intent.extras as Bundle
            extras.getString("alarm_id")?.let { alarmManager.deactivateAlarm(it) }

            if (isTaskRoot) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        }

        binding.snoozeAlarmButton.setOnClickListener { view: View ->
            println("Snooze button clicked")

            val wkbApp = applicationContext as WakeUpBuddyApp
            val alarmManager = wkbApp.getMyAlarmManager()

            val extras = intent.extras as Bundle
            extras.getString("alarm_id")?.let { alarmManager.snoozeAlarm(it) }

            if (isTaskRoot) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()

        }
    }

    private fun getWeekdayName(day: Int): String {
        var name = ""
        when (day) {
            1 -> name = "Sunday"
            2 -> name = "Monday"
            3 -> name = "Tuesday"
            4 -> name = "Wednesday"
            5 -> name = "Thursday"
            6 -> name = "Friday"
            7 -> name = "Saturday"
        }
        return name
    }

    private fun getMonthName(month: Int): String {
        var name = ""
        when (month) {
            0 -> name = "January"
            1 -> name = "February"
            2 -> name = "March"
            3 -> name = "April"
            4 -> name = "May"
            5 -> name = "June"
            6 -> name = "July"
            7 -> name = "August"
            8 -> name = "September"
            9 -> name = "October"
            10 -> name = "November"
            11 -> name = "December"
        }
        return name
    }

}