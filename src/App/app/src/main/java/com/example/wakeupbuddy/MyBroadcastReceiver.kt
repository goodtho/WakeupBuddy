package com.example.wakeupbuddy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import java.text.SimpleDateFormat
import java.util.*


class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        //todo implement alarm (vibration & sound)

        println(
            "${
                SimpleDateFormat.getTimeInstance().format(Calendar.getInstance().time)
            } Broadcast received!"
        )
        if (intent!!.action.equals("com.wakeupbuddy.alarm")) {
            val extras: Bundle = intent.extras as Bundle
            println("Alarm ${extras.get("name")} is ringing!")

            if (Build.VERSION.SDK_INT >= 31) {
                val effectId: Int = VibrationEffect.Composition.PRIMITIVE_LOW_TICK
                val vibratorManager: VibratorManager =
                    context?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                if (vibratorManager.defaultVibrator.areAllPrimitivesSupported(effectId)) {
                    vibratorManager.vibrate(
                        CombinedVibration.createParallel(
                            VibrationEffect.startComposition()
                                .addPrimitive(effectId)
                                .compose()))
                }
            } else {
                //deprecated in API 26
                val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(500)
            }

            var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
            // setting default ringtone
            val ringtone: Ringtone = RingtoneManager.getRingtone(context, alarmUri)
            // play ringtone
            ringtone.play()

        } else if (intent!!.action.equals("android.intent.action.BOOT_COMPLETED")) {
            println("Boot completed!")
        }
    }
}