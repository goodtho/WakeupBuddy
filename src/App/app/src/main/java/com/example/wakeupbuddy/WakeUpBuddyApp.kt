package com.example.wakeupbuddy

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

class WakeUpBuddyApp : Application() {
    private lateinit var ringtone: Ringtone

    fun getRingtone(): Ringtone {
        return ringtone
    }

    fun setRingtone() {
        var notification: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (notification == null) {
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        ringtone = RingtoneManager.getRingtone(this.applicationContext, notification)
    }
}