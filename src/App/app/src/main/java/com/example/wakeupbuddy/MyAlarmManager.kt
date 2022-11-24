package com.example.wakeupbuddy

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import kotlinx.android.synthetic.main.row_item_alarm.view.*
import java.util.*


class MyAlarmManager(private val context: Context) : BaseAdapter() {

    private val alarmList: ArrayList<Alarm> = ArrayList()
    private var alarmManager: AlarmManager
    private lateinit var ringtone: Ringtone

    init {
        //todo get all alarms of the user from the database

        alarmList.add(Alarm("Alarm 1", Calendar.getInstance(), true))
//        val calendar = Calendar.getInstance()
//        calendar.add(Calendar.MINUTE, 10)
//        alarmList.add(Alarm("Alarm 2", calendar, true))

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        setRingtone() //set default ringtone

        alarmList.forEachIndexed { index, alarm ->
            if (alarm.isActive) {
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
        alarmTimeTextView.text = "${alarmList[position].date.get(Calendar.HOUR)} : ${
            alarmList[position].date.get(Calendar.MINUTE)
        }"

        val switchCompat: SwitchCompat = view.alarm_switch
        switchCompat.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            if (isChecked && !alarmList[position].isActive) {
                activateAlarm(position)
            } else if (!isChecked && alarmList[position].isActive) {
                deactivateAlarm(alarmList[position].id)
            }
        }

        switchCompat.isChecked = alarmList[position].isActive

        return view
    }

    fun createAlarm(name: String, hours: Int, minutes: Int) {
        val alarmTime = Calendar.getInstance()
        alarmTime.set(Calendar.HOUR_OF_DAY, hours)
        alarmTime.set(Calendar.MINUTE, minutes)
        val alarm = Alarm(name, alarmTime, true)
        alarmList.add(alarm)
        activateAlarm(alarmList.size - 1)
        println("Alarm ${alarm.name} for ${alarm.date.get(Calendar.HOUR)}:${alarm.date.get(Calendar.MINUTE)} created")
    }

    fun activateAlarm(position: Int) {
        val intent = Intent(context.applicationContext, MyBroadcastReceiver::class.java)
        intent.putExtra("name", alarmList[position].name)
        intent.putExtra("alarm_id", alarmList[position].id.toString())
        intent.action = "com.wakeupbuddy.alarm"
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            alarmList[position].date.timeInMillis,
            pendingIntent
        )

        println("Alarm ${alarmList[position].name} activated")
        println(
            "Alarm set to ${alarmList[position].date.get(Calendar.HOUR)}:${
                alarmList[position].date.get(
                    Calendar.MINUTE
                )
            }"
        )
    }

    fun deactivateAlarm(alarm_id: UUID) {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.action = "com.wakeupbuddy.alarm"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.cancel(pendingIntent)

        if (ringtone.isPlaying) ringtone.stop()

        alarmList.forEachIndexed { index, alarm ->
            if (alarm.id.equals(alarm_id)) {
                alarmList[index].isActive = false
                println("Alarm ${alarmList[index].name} deactivated")
            }
        }
        notifyDataSetChanged()

        println("Alarm stopped")
    }

    fun setRingtone(type: Int = RingtoneManager.TYPE_ALARM) {
        var notification: Uri? = RingtoneManager.getDefaultUri(type)
        if (notification == null) {
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        ringtone = RingtoneManager.getRingtone(context, notification)
    }

    fun playRingtone() {
        ringtone.play()
    }

}