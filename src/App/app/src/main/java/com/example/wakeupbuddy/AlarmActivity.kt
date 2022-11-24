package com.example.wakeupbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeupbuddy.databinding.ActivityAlarmBinding
import kotlinx.android.synthetic.main.row_item_alarm.view.*
import java.util.*

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bDeactivateAlarm.setOnClickListener { view: View ->
            println("Button clicked")

            val wkbApp = applicationContext as WakeUpBuddyApp
            val alarmManager = wkbApp.getMyAlarmManager()

            val extras = intent.extras as Bundle
            alarmManager.deactivateAlarm(UUID.fromString(extras.getString("alarm_id")))

            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

}