package com.example.wakeupbuddy

import android.os.Bundle
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wakeupbuddy.databinding.FragmentAddAlarmBinding


class add_alarm : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddAlarmBinding.inflate(layoutInflater)
        binding.btnLogout.setOnClickListener {
            val intent = Intent(this@add_alarm.requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }


}