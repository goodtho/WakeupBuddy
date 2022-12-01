package com.example.wakeupbuddy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.*
import com.example.wakeupbuddy.activities.AlarmActivity
import java.text.SimpleDateFormat
import java.util.*


class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        println(
            "${
                SimpleDateFormat.getTimeInstance().format(Calendar.getInstance().time)
            } Broadcast received!"
        )
        if (intent!!.action.equals("com.wakeupbuddy.alarm")) {
            val extras: Bundle = intent.extras as Bundle
            println("Alarm ${extras.get("name")} is ringing!")

            val wkbApp = context?.applicationContext as WakeUpBuddyApp
            if (wkbApp.vibrationActivated()) { // set and start vibration
                if (Build.VERSION.SDK_INT >= 31) {
                    val effectId: Int = VibrationEffect.Composition.PRIMITIVE_LOW_TICK
                    val vibratorManager: VibratorManager =
                        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                    if (vibratorManager.defaultVibrator.areAllPrimitivesSupported(effectId)) {
                        vibratorManager.vibrate(
                            CombinedVibration.createParallel(
                                VibrationEffect.startComposition()
                                    .addPrimitive(effectId)
                                    .compose()
                            )
                        )
                    }
                } else {
                    //deprecated in API 26
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(500)
                }
            }

            // set and play ringtone
            wkbApp.getAlarmTone().play()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                wkbApp.getAlarmTone().isLooping = true
            }

            // initialize specific alarm activity
            val alarmIntent = Intent(context.applicationContext, AlarmActivity::class.java)
            alarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            println("Wert: ${extras.getString("alarm_id")}")
            alarmIntent.putExtra("alarm_id", extras.getString("alarm_id"))

            context.startActivity(alarmIntent)

        } else if (intent.action.equals("android.intent.action.BOOT_COMPLETED")) {
            println("Boot completed!")

            //todo after booting the smartphone, activate all alarms that are set active

        }
    }
}