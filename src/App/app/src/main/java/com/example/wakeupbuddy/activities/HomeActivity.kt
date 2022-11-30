package com.example.wakeupbuddy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.*
import com.example.wakeupbuddy.databinding.ActivityHomeBinding
import com.example.wakeupbuddy.fragments.MyAlarmsFragment
import com.example.wakeupbuddy.fragments.MyFriends
import com.example.wakeupbuddy.fragments.MyHome
import com.example.wakeupbuddy.fragments.MySettingsFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding //1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)  //2

        setContentView(binding.root)
        replaceFragment(MyAlarmsFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.add_alarm -> replaceFragment(MyAlarmsFragment())
                R.id.home_appliances -> replaceFragment(MyHome())
                R.id.add_friends -> replaceFragment(MyFriends())
                R.id.settings -> replaceFragment(MySettingsFragment())
                else -> {}
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, fragment)
            fragmentTransaction.commit()
    }

}


