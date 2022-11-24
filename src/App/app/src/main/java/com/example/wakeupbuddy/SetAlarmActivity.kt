package com.example.wakeupbuddy

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TimePicker
import com.example.wakeupbuddy.databinding.ActivitySetAlarmTimeBinding
import kotlinx.android.synthetic.main.activity_set_alarm_time.view.*

class SetAlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetAlarmTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm_time)

        binding = ActivitySetAlarmTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener { view: View ->
            val parentView = view.parent as ViewGroup
            val timePicker = parentView.time_picker as TimePicker
            val alarmNameInput = parentView.alarm_name_input as EditText
            val alarmName = alarmNameInput.text.toString()

            val wkbApp = applicationContext as WakeUpBuddyApp
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var hours: Int = timePicker.hour
                wkbApp.getMyAlarmManager().createAlarm(alarmName, hours, timePicker.minute)
            } else {
                var hours: Int = timePicker.currentHour
                wkbApp.getMyAlarmManager().createAlarm(alarmName, hours, timePicker.currentMinute)
            }
            wkbApp.getMyAlarmManager().notifyDataSetChanged()

            finish()
        }

    }
}