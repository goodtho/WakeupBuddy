package com.example.wakeupbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding //1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)  //2
        setContentView(binding.root)
        replaceFragment(add_alarm())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.add_alarm -> replaceFragment(add_alarm())
                R.id.home_appliances -> replaceFragment(myhome())
                R.id.add_friends -> replaceFragment(friends())
                R.id.settings -> replaceFragment(settings())
                else -> {


                }

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


