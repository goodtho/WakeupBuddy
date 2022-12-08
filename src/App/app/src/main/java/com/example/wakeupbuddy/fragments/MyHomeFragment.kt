package com.example.wakeupbuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.databinding.FragmentMyDevicesBinding

class MyHomeFragment : Fragment() {
    private lateinit var binding: FragmentMyDevicesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyDevicesBinding.inflate(layoutInflater)
        return binding.root
    }

}