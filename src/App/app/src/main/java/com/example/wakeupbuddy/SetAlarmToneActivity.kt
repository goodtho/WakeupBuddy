package com.example.wakeupbuddy

import java.util.*
import android.content.Context
import android.database.Cursor
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeupbuddy.databinding.ActivitySetAlarmToneBinding
import kotlinx.android.synthetic.main.activity_set_alarm_tone.view.*
import kotlinx.android.synthetic.main.row_item_alarm_tone.view.*
import kotlin.collections.ArrayList

class SetAlarmToneActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetAlarmToneBinding
    private lateinit var myATManager: MyAlarmToneManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmToneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmTonesLV: ListView = binding.root.alarm_tone_list
        myATManager = MyAlarmToneManager(this@SetAlarmToneActivity)
        alarmTonesLV.adapter = myATManager

        //todo create adapter for listview and present all selectable alarmtones
        //todo set onclicklistener: if an alarmtone was selected, adjust global settings object
        //todo (create global settings object which is initialized when the app is started)

    }

    inner class MyAlarmToneManager(val context: Context) : BaseAdapter() {

        private val alarmToneList: ArrayList<Pair<String, String>> = ArrayList()

        init {
            val manager = RingtoneManager(context)
            manager.setType(RingtoneManager.TYPE_ALARM)
            val cursor: Cursor = manager.cursor
            while (cursor.moveToNext()) {
                val alarmTitle: String = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
                val alarmUri: String = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(
                    RingtoneManager.ID_COLUMN_INDEX)
                alarmToneList.add(Pair(alarmTitle, alarmUri))
            }
        }

        override fun getCount(): Int {
            return alarmToneList.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.row_item_alarm_tone, parent, false)

            val alarmToneName = view.alarm_tone_name
            alarmToneName.text = alarmToneList[position].first
//            val alarmToneUri = view.alarm_tone_uri
//            alarmToneUri.text = alarmToneList[position].second

            val selectButton: ImageButton = view.select_alarm_tone_button
            selectButton.setOnClickListener {
                val wkbApp = context.applicationContext as WakeUpBuddyApp
                val uri = Uri.parse(alarmToneList[position].second)
                wkbApp.setRingtone(uri)
                println("New alarm tone ${alarmToneList[position].first} set")
                finish()
            }

            return view
        }

    }

}