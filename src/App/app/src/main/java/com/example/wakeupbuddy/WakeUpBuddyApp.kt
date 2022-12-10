package com.example.wakeupbuddy

import android.app.Application
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import com.example.wakeupbuddy.models.FriendConnectionModel
import com.example.wakeupbuddy.models.SettingsModel
import java.com.example.wakeupbuddy.models.UserModel
import java.util.*
import kotlin.collections.ArrayList

class WakeUpBuddyApp : Application() {

    private var myAMInitalized: Boolean = false
    private lateinit var myAM: MyAlarmManager
    private lateinit var mySettings: SettingsModel
    private lateinit var myFGM: MyFriendGroupManager
    private var myFGMInitialized: Boolean = false
    private var userList: ArrayList<UserModel> = ArrayList()
    private var currentUser: UserModel? = null
    private val api = RetrofitHelper.getInstance().create(ApiInterface::class.java)

    fun initializeMyAlarmManager(context: Context) {

        //todo get user settings from the db and initialize global (wkbApp) settings object

        val prefs = getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
        val uri = Uri.parse(
            prefs.getString(
                "AlarmToneUri",
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString()
            )
        )
        val ringtone = RingtoneManager.getRingtone(applicationContext, uri)
        mySettings = SettingsModel(
            prefs.getBoolean("VibrationActivated", true),
            TimeZone.getTimeZone(prefs.getString("TimezoneId", TimeZone.getDefault().id)),
            ringtone
        )

        myAM = MyAlarmManager(context)
        myAMInitalized = true
    }

    fun initializeMyFriendGroupManager(context: Context) {
        myFGM = MyFriendGroupManager(context)
        myFGMInitialized = true
    }

    fun getApi(): ApiInterface {
        return api
    }

    fun getMyAlarmManager(): MyAlarmManager = myAM

    fun myAlarmManagerIsInitialized(): Boolean = myAMInitalized

    fun getMyFriendGroupManager(): MyFriendGroupManager = myFGM

    fun myFriendGroupManagerIsInitialized(): Boolean = myFGMInitialized

    fun getAlarmTone(): Ringtone {
        return mySettings.ringtone
    }

    fun setAlarmTone(uri: Uri) {
        mySettings.ringtone = RingtoneManager.getRingtone(applicationContext, uri)
        val settings = getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
        val editor = settings.edit()
        editor.putString("AlarmToneUri", uri.toString())
        editor.apply()
    }

    fun vibrationActivated(): Boolean = mySettings.vibration

    fun setVibration(state: Boolean) {
        mySettings.vibration = state
        val settings = getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
        val editor = settings.edit()
        editor.putBoolean("VibrationActivated", state)
        editor.apply()
    }

    fun getTimezone(): TimeZone = mySettings.timezone

    fun setTimezone(region: String) {
        mySettings.timezone = TimeZone.getTimeZone(region)
        val settings = getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
        val editor = settings.edit()
        editor.putString("TimezoneId", region)
        editor.apply()
    }

    private fun loadUserList() {
        val settings = getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
        val usersAsStrings: Set<String>? = settings.getStringSet("Users", null)
        usersAsStrings?.forEach { user: String ->
            val userData = user.split(SPLIT_SYMBOL)
            val id = userData[0]
            val username = userData[1]
            val password = userData[2]
            val email = userData[3]
            val name = userData[4]
            userList.add(UserModel(id, name, username, password, email))
        }
    }

    fun getUser(userID: String): UserModel? {
        if (userList.isEmpty()) {
            loadUserList()
        }

        if (userID != "") {
            userList.forEach { user: UserModel ->
                if (user.username == userID || user.email == userID || user.id == userID) {
                    return user
                }
            }
        }
        return null
    }

    fun getUserList(): ArrayList<UserModel> = userList

    fun userExists(userID: String): Boolean {
        if (userList.isEmpty()) {
            loadUserList()
        }

        if (userID != "") {
            userList.forEach { user: UserModel ->
                if (user.username == userID || user.email == userID || user.id == userID) {
                    return true
                }
            }
        }
        return false
    }

    fun createUser(user: UserModel) {
        if (!userExists(user.email) && !userExists(user.username)) {
            userList.add(user)
            persistUserList()
        }
    }

    private fun persistUserList() {
        val settings = getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
        val editor = settings.edit()
        val usersAsSet: MutableSet<String> = mutableSetOf()
        userList.forEach { user: UserModel ->
            val userAsString = "${user.id}$SPLIT_SYMBOL${user.username}$SPLIT_SYMBOL" +
                "${user.password}$SPLIT_SYMBOL${user.email}$SPLIT_SYMBOL${user.name}"
            usersAsSet.add(userAsString)
        }
        editor.putStringSet("Users", usersAsSet)
        editor.apply()
    }

    fun setCurrentUser(user: UserModel?) {
        currentUser = user
    }

    fun getCurrentUser(): UserModel? = currentUser

    companion object {
        const val SHARED_PREFERENCES_NAME = "UserInfo"
        const val SPLIT_SYMBOL = "/-"
    }

}