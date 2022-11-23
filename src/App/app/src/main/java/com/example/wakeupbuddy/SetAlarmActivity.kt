package com.example.wakeupbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wakeupbuddy.databinding.ActivitySetAlarmTimeBinding

class SetAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm_time)

        val binding = ActivitySetAlarmTimeBinding.inflate(layoutInflater)
        binding.nextButton.setOnClickListener {

        }

    }
}