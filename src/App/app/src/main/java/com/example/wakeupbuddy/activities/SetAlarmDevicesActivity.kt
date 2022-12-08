package com.example.wakeupbuddy.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.wakeupbuddy.R
import com.example.wakeupbuddy.databinding.ActivitySetAlarmDevicesBinding

class SetAlarmDevicesActivity : AppCompatActivity() {
    private lateinit var binding:  ActivitySetAlarmDevicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeApplianceContainer1.setOnClickListener { view: View ->
            if (view.background.constantState == ResourcesCompat.getDrawable(resources, R.drawable.container_white, theme)?.constantState) {
                view.setBackgroundResource(R.drawable.container_green)
            } else {
                view.setBackgroundResource(R.drawable.container_white)
            }
        }

        binding.homeApplianceContainer2.setOnClickListener { view: View ->
            if (view.background.constantState == ResourcesCompat.getDrawable(resources, R.drawable.container_white, theme)?.constantState) {
                view.setBackgroundResource(R.drawable.container_green)
            } else {
                view.setBackgroundResource(R.drawable.container_white)
            }
        }

        binding.homeApplianceContainer3.setOnClickListener { view: View ->
            if (view.background.constantState == ResourcesCompat.getDrawable(resources, R.drawable.container_white, theme)?.constantState) {
                view.setBackgroundResource(R.drawable.container_green)
            } else {
                view.setBackgroundResource(R.drawable.container_white)
            }
        }

        binding.setAlarmButton.setOnClickListener { view: View ->
            finish()
        }
    }

}