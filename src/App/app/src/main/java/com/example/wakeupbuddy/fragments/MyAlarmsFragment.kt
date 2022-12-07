package com.example.wakeupbuddy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.activities.LoginActivity
import com.example.wakeupbuddy.activities.SetAlarmActivity
import com.example.wakeupbuddy.databinding.FragmentMyAlarmsBinding
import kotlinx.android.synthetic.main.fragment_my_alarms.view.*

class MyAlarmsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMyAlarmsBinding.inflate(layoutInflater)
        binding.btnLogout.setOnClickListener {
            val wkbApp = context?.applicationContext as WakeUpBuddyApp
            wkbApp.setCurrentUser(null)
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

            //todo implement only admin can deactivate alarm
        } else {
            view.alarm_list.adapter = wkbApp.getMyAlarmManager()
        }

        wkbApp.initializeMyFriendGroupManager(this@MyAlarmsFragment.requireContext().applicationContext)

        val user = wkbApp.getCurrentUser()
        view.home_greeting.text = "Hello${" " + user?.username}!"
    }

}
