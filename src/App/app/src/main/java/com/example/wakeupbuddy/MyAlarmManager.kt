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

        alarmList.add(Alarm(name="Alarm 1", date=Calendar.getInstance()))
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 10)
        alarmList.add(Alarm(name="Alarm 2", date=calendar))

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        setRingtone() //set default ringtone

        //todo add alarm implementieren

    }

    override fun getCount(): Int {
        return  alarmList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.row_item_alarm, parent, false)

        val alarmNameTextView: TextView = view.alarm_name
        alarmNameTextView.text = alarmList[position].name

        val alarmTimeTextView: TextView = view.alarm_time
        alarmTimeTextView.text = "${alarmList[position].date.get(Calendar.HOUR)} : ${alarmList[position].date.get(Calendar.MINUTE)}"

        val switchCompat: SwitchCompat = view.alarm_switch
        switchCompat.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                println("Postiton: $position & Name: ${alarmList[position].name}")
                this.activateAlarm(position)
            } else {
                this.stopAlarm()
            }
        }

        return view
    }

    fun activateAlarm(position: Int) {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.putExtra("name", alarmList[position].name)
        intent.action = "com.wakeupbuddy.alarm"
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmList[position].date.timeInMillis, pendingIntent)
        println("Alarm ${alarmList[position].name} activated")
    }

    fun stopAlarm() {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.cancel(pendingIntent)

        ringtone.stop()

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