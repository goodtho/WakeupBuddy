package com.example.wakeupbuddy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.databinding.FragmentAddAlarmBinding
import kotlinx.android.synthetic.main.fragment_add_alarm.view.*

class MyAlarmsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddAlarmBinding.inflate(layoutInflater)
        binding.btnLogout.setOnClickListener {
            val intent = Intent(this@MyAlarmsFragment.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val intent = Intent(this@MyAlarmsFragment.requireContext(), SetAlarmActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wkbApp = context?.applicationContext as WakeUpBuddyApp
        if (!wkbApp.myAlarmManagerIsInitialized()) {
            val alarmListView: ListView = view.alarm_list
            wkbApp.initializeMyAlarmManager(this@MyAlarmsFragment.requireContext())
            alarmListView.adapter = wkbApp.getMyAlarmManager()
        }
    }
}
