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

        binding.bDeactivateAlarm.setOnClickListener { view: View ->
            println("Button clicked")

            val wkbApp = applicationContext as WakeUpBuddyApp
            val alarmManager = wkbApp.getMyAlarmManager()

            val extras = intent.extras as Bundle
            alarmManager.deactivateAlarm(UUID.fromString(extras.getString("alarm_id")))

            if (isTaskRoot) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        }
    }

}