package com.example.wakeupbuddy

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import kotlinx.android.synthetic.main.row_item_alarm.view.*
import java.com.example.wakeupbuddy.models.AlarmModel
import java.util.*


class MyAlarmManager(private val context: Context) : BaseAdapter() {

    private val alarmList: ArrayList<AlarmModel> = ArrayList()
    private var alarmManager: AlarmManager
    private val wkbApp: WakeUpBuddyApp = context.applicationContext as WakeUpBuddyApp

    init {
        //todo get all alarms of the user from the database

        val calendar = Calendar.getInstance()
        calendar.timeZone = wkbApp.getTimezone()
        val time = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
        alarmList.add(AlarmModel(name="Alarm 1", time=time, active=0))

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmList.forEachIndexed { index, alarm ->
            if (alarm.active == 1) {
                activateAlarm(index)
            }
        }

        //todo sort by activation (1) and time (2)
        //todo make listview scrollable, currently: to many alarms -> layout broken

    }

    override fun getCount(): Int {
        return alarmList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.row_item_alarm, parent, false)

        val alarmNameTextView: TextView = view.alarm_name
        alarmNameTextView.text = alarmList[position].name

        val alarmTimeTextView: TextView = view.alarm_time
        alarmTimeTextView.text = alarmList[position].time

        val switchCompat: SwitchCompat = view.alarm_switch
        switchCompat.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            if (isChecked && (alarmList[position].active != 1)) {
                activateAlarm(position)
            } else if (!isChecked && (alarmList[position].active == 1)) {
                deactivateAlarm(UUID.fromString(alarmList[position].id))
            }
        }

        switchCompat.isChecked = (alarmList[position].active == 1)

        return view
    }

    fun createAlarm(name: String, hours: Int, minutes: Int) {
        val alarmTime = Calendar.getInstance()

        alarmTime.set(Calendar.HOUR_OF_DAY, hours)
        alarmTime.set(Calendar.MINUTE, minutes)
        val time = "$hours:$minutes"

        val alarm = AlarmModel(name=name, time=time, active=1)
        alarmList.add(alarm)
        activateAlarm(alarmList.size - 1)

        println("Alarm ${alarm.name} for $time created")
    }

    private fun activateAlarm(position: Int) {
        val intent = Intent(context.applicationContext, MyBroadcastReceiver::class.java)
        intent.putExtra("name", alarmList[position].name)
        intent.putExtra("alarm_id", alarmList[position].id.toString())
        intent.action = "com.wakeupbuddy.alarm"
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val timeData = alarmList[position].time.split(":")
        val time = Calendar.getInstance()
        time.timeZone = wkbApp.getTimezone()
        time.set(Calendar.HOUR_OF_DAY, timeData[0].toInt()) //set hours
        time.set(Calendar.MINUTE, timeData[1].toInt()) //set minutes

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time.timeInMillis,
            pendingIntent
        )

        println("Alarm ${alarmList[position].name} activated")
        println(
            "Alarm set to ${alarmList[position].time}"
        )
        println("Time in Millis: ${time.timeInMillis}")
    }

    fun deactivateAlarm(alarm_id: UUID) {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.action = "com.wakeupbuddy.alarm"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.cancel(pendingIntent)

        if (wkbApp.getAlarmTone().isPlaying) wkbApp.getAlarmTone().stop()

        alarmList.forEachIndexed { index, alarm ->
            if (alarm.id.equals(alarm_id)) {
                alarmList[index].active = 0
                println("Alarm ${alarmList[index].name} deactivated")
            }
        }
        notifyDataSetChanged()

        println("Alarm stopped")
    }

}