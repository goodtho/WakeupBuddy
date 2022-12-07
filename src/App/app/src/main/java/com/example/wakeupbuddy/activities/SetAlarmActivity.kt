package com.example.wakeupbuddy.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TimePicker
import com.example.wakeupbuddy.R
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.databinding.ActivitySetAlarmTimeBinding
import kotlinx.android.synthetic.main.activity_set_alarm_time.view.*
import java.util.Calendar

class SetAlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetAlarmTimeBinding
    private lateinit var wkbApp: WakeUpBuddyApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //if the user wants to use the app in a different time zone
        wkbApp = applicationContext as WakeUpBuddyApp
        val curTime = Calendar.getInstance()
        curTime.timeZone = wkbApp.getTimezone()
        binding.timePicker.setIs24HourView(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.timePicker.hour = curTime.get(Calendar.HOUR_OF_DAY)
            binding.timePicker.minute = curTime.get(Calendar.MINUTE)
        } else {
            binding.timePicker.currentHour = curTime.get(Calendar.HOUR_OF_DAY)
            binding.timePicker.currentMinute = curTime.get(Calendar.MINUTE)
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent(this@SetAlarmActivity, SetAlarmFriendsActivity::class.java)
            val name = binding.alarmNameInput.text.toString()
            intent.putExtra("Name", name)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val hours: Int = binding.timePicker.hour //returns hours in the 24h format
                val minutes: Int = binding.timePicker.minute
                intent.putExtra("Time", "$hours:$minutes")
            } else {
                val hours: Int = binding.timePicker.currentHour
                val minutes: Int = binding.timePicker.currentMinute
                intent.putExtra("Time", "$hours:$minutes")
            }
            startActivity(intent)
            finish()
        }

        binding.setAlarmButton.setOnClickListener {
            val alarmNameInput = binding.alarmNameInput
            val alarmName = alarmNameInput.text.toString()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val hours: Int = binding.timePicker.hour //returns hours in the 24h format
                wkbApp.getMyAlarmManager().createAlarm(alarmName, hours, binding.timePicker.minute, "")
            } else {
                val hours: Int = binding.timePicker.currentHour
                wkbApp.getMyAlarmManager().createAlarm(alarmName, hours, binding.timePicker.currentMinute, "")
            }
            wkbApp.getMyAlarmManager().notifyDataSetChanged()

            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}