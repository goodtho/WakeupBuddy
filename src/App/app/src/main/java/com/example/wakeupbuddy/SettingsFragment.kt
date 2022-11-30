package com.example.wakeupbuddy

import android.content.Intent
import android.database.Cursor
import android.media.RingtoneManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.databinding.ActivityMainBinding
import com.example.wakeupbuddy.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

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
            val intent = Intent(this@SettingsFragment.requireContext(), SetAlarmToneActivity::class.java)
            startActivity(intent)
        }

        binding.setVibrationButton.isChecked = wkbApp.vibrationActivated()
        binding.setVibrationButton.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            wkbApp.setVibration(isChecked)
            println("Vibration set to $isChecked")
        }

        binding.selectTimezoneName.text = wkbApp.getTimezone().id
        binding.selectTimezoneButton.setOnClickListener {
            val intent = Intent(this@SettingsFragment.requireContext(), SetTimezoneActivity::class.java)
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
