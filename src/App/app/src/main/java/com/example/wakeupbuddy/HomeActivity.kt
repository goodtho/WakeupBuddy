package com.example.wakeupbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.fragment_add_alarm.view.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding //1
    private var myAlarmManager: MyAlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)  //2

        setContentView(binding.root)
        replaceFragment(MyAlarmsFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.add_alarm -> replaceFragment(MyAlarmsFragment())
                R.id.home_appliances -> replaceFragment(myhome())
                R.id.add_friends -> replaceFragment(friends())
                R.id.settings -> replaceFragment(settings())
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


