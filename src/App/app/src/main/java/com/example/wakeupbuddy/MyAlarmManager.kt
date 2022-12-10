package com.example.wakeupbuddy

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import kotlinx.android.synthetic.main.row_item_alarm.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.com.example.wakeupbuddy.models.AlarmModel
import java.com.example.wakeupbuddy.models.UserModel
import java.util.*


class MyAlarmManager(private val context: Context) : BaseAdapter() {

    private val alarmList: ArrayList<AlarmModel> = ArrayList()
    private var alarmManager: AlarmManager
    private val wkbApp: WakeUpBuddyApp = context.applicationContext as WakeUpBuddyApp

    init {
        //todo get all alarms of the user from the database
        loadAlarms()

        val calendar = Calendar.getInstance()
        calendar.timeZone = wkbApp.getTimezone()
        val time = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
        alarmList.add(AlarmModel(name = "Alarm 1", time = time, active = 0))

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmList.forEachIndexed { index, alarm ->
            if (alarm.active == 1) {
                activateAlarm(index)
            }
        }

        //todo sort by activation (1) and time (2)
        //todo make listview scrollable, currently: to many alarms -> layout broken

    }

    private fun loadAlarms() = runBlocking {
        val test = launch {
            val api = wkbApp.getApi()
            val res = api.getAllAlarms()
            println(res)
            val list = res.body()
        }
        test.join()
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
                deactivateAlarm(alarmList[position].id)
            }
        }
        switchCompat.isChecked = (alarmList[position].active == 1)

        if (alarmList[position].groupID != "") {
            val groupMembers =
                wkbApp.getMyFriendGroupManager().getMembersOfGroup(alarmList[position].groupID)
            if (groupMembers.isNotEmpty()) {
                groupMembers.remove(wkbApp.getCurrentUser())
                var withMembers = "With "
                groupMembers.forEach { member: UserModel ->
                    withMembers += member.username
                }
                val alarmMember = view.alarm_member
                alarmMember.text = withMembers
                alarmMember.visibility = TextView.VISIBLE
            }
        }

        return view
    }

    fun createAlarm(name: String, hours: Int, minutes: Int, group: String) {
        val alarmTime = Calendar.getInstance()

        alarmTime.set(Calendar.HOUR_OF_DAY, hours)
        alarmTime.set(Calendar.MINUTE, minutes)
        val time = "$hours:$minutes"

        val alarm = AlarmModel(name = name, time = time, active = 1, groupID = group)
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

        var flags = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, flags)

        val timeData = alarmList[position].time.split(":")
        val time = Calendar.getInstance()
        time.timeZone = wkbApp.getTimezone()
        time.set(Calendar.HOUR_OF_DAY, timeData[0].toInt()) //set hours
        time.set(Calendar.MINUTE, timeData[1].toInt()) //set minutes

        if (time.timeInMillis < Calendar.getInstance().timeInMillis) {
            time.add(Calendar.DAY_OF_YEAR, 1)
            println("Alarm scheduled for tomorrow")
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time.timeInMillis,
            pendingIntent
        )

        println("Alarm ${alarmList[position].name} activated")
        println(
            "Alarm set to ${alarmList[position].time}"
        )
    }

    fun deactivateAlarm(alarm_id: String) {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.action = "com.wakeupbuddy.alarm"
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        alarmManager.cancel(pendingIntent)

        if (wkbApp.getAlarmTone().isPlaying) wkbApp.getAlarmTone().stop()

        var position = -1
        alarmList.forEachIndexed { index, alarm ->
            if (alarm.id == alarm_id) {
                alarmList[index].active = 0
                println("Alarm ${alarmList[index].name} deactivated")
                position = index
            }
        }
        notifyDataSetChanged()

        println("Alarm ${alarmList[position].name} stopped")
        println(
            "Alarm set to ${alarmList[position].time}"
        )
    }

    fun snoozeAlarm(alarm_id: String) {
        val intent =
            Intent(context.applicationContext, MyBroadcastReceiver::class.java) //or just context
        intent.action = "com.wakeupbuddy.alarm"
        val cancelAlarmPI = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.cancel(cancelAlarmPI)

        if (wkbApp.getAlarmTone().isPlaying) wkbApp.getAlarmTone().stop()

        var position = -1
        alarmList.forEachIndexed { index, alarm ->
            if (alarm_id == alarmList[index].id) position = index
        }

//        val intent = Intent(context.applicationContext, MyBroadcastReceiver::class.java)
        intent.putExtra("name", alarmList[position].name)
        intent.putExtra("alarm_id", alarmList[position].id.toString())
//        intent.action = "com.wakeupbuddy.alarm"
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        var flags = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }
        val createAlarmPI =
            PendingIntent.getBroadcast(context, 0, intent, flags)

//        val timeData = alarmList[position].time.split(":")
//        val time = Calendar.getInstance()
//        time.timeZone = wkbApp.getTimezone()
//        time.set(Calendar.HOUR_OF_DAY, timeData[0].toInt()) //set hours
//        time.set(Calendar.MINUTE, timeData[1].toInt()) //set minutes

        val time = Calendar.getInstance()
        time.add(Calendar.MINUTE, 3)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time.timeInMillis,
            createAlarmPI
        )

        println("Alarm ${alarmList[position].name} snoozed")
        println(
            "Alarm set to ${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}"
        )
    }

}