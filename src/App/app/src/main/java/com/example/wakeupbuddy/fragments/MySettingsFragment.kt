package com.example.wakeupbuddy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.activities.SetAlarmToneActivity
import com.example.wakeupbuddy.activities.SetTimezoneActivity
import com.example.wakeupbuddy.databinding.FragmentSettingsBinding

class MySettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        val wkbApp = context?.applicationContext as WakeUpBuddyApp
        binding.setAlarmToneName.text = wkbApp.getAlarmTone().getTitle(context)
        binding.setAlarmToneButton.setOnClickListener {
            val intent = Intent(this@MySettingsFragment.requireContext(), SetAlarmToneActivity::class.java)
            startActivity(intent)
        }

        binding.setVibrationButton.isChecked = wkbApp.vibrationActivated()
        binding.setVibrationButton.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            wkbApp.setVibration(isChecked)
            println("Vibration set to $isChecked")
        }

        binding.selectTimezoneName.text = wkbApp.getTimezone().id
        binding.selectTimezoneButton.setOnClickListener {
            val intent = Intent(this@MySettingsFragment.requireContext(), SetTimezoneActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val wkbApp = context?.applicationContext as WakeUpBuddyApp
        if (wkbApp.getAlarmTone().getTitle(context) != binding.setAlarmToneName.text)
            binding.setAlarmToneName.text = wkbApp.getAlarmTone().getTitle(context)
        if (wkbApp.getTimezone().id != binding.selectTimezoneName.text)
            binding.selectTimezoneName.text = wkbApp.getTimezone().id
    }

}
