package com.example.wakeupbuddy.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeupbuddy.R
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.databinding.ActivitySetTimezoneBinding
import kotlinx.android.synthetic.main.activity_set_timezone.view.*
import kotlinx.android.synthetic.main.row_item_with_button.view.*
import java.util.TimeZone

class SetTimezoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetTimezoneBinding
    private lateinit var myTZManager: MyTimezoneManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetTimezoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timezonesLV: ListView = binding.root.timezone_list
        myTZManager = MyTimezoneManager(this@SetTimezoneActivity)
        timezonesLV.adapter = myTZManager

        // todo implement search function for specific timezone

    }

    inner class MyTimezoneManager(val context: Context) : BaseAdapter() {

        private val timezoneList: Array<String> = TimeZone.getAvailableIDs()

        override fun getCount(): Int {
            return timezoneList.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.row_item_with_button, parent, false)

            view.element_name.text = timezoneList[position]

            val selectButton: ImageButton = view.select_button
            selectButton.setOnClickListener {
                val wkbApp = context.applicationContext as WakeUpBuddyApp
                wkbApp.setTimezone(timezoneList[position])
                println("New timezone set to ${timezoneList[position]}")
                finish()
            }

            return view
        }

    }
}