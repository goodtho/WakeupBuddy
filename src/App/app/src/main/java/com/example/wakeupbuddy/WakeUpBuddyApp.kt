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
    private var userList: ArrayList<UserModel> = ArrayList()
    private var currentUser: UserModel? = null
    private var friendList: ArrayList<FriendConnectionModel> = ArrayList()

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

        myAMInitalized = true
        myAM = MyAlarmManager(context)
    }

    fun getMyAlarmManager(): MyAlarmManager = myAM

    fun myAlarmManagerIsInitialized(): Boolean = myAMInitalized

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

    private fun loadFriendConnections() {
        val settings = getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
        val friendConnectionsAsStrings: Set<String>? = settings.getStringSet("FriendConnections", null)
        friendConnectionsAsStrings?.forEach { connection: String ->
            val connectionData = connection.split(SPLIT_SYMBOL)
            friendList.add(FriendConnectionModel(connectionData[0], connectionData[1]))
        }
    }

    fun getFriendsOfCurrentUser(): ArrayList<UserModel> {
        if (friendList.isEmpty()) {
            loadFriendConnections()
        }

        val friends = ArrayList<UserModel>()
        friendList.forEach { con: FriendConnectionModel ->
            if (con.friend1ID == currentUser?.id || con.friend2ID == currentUser?.id) {
                val id = if (con.friend1ID != currentUser?.id) con.friend1ID else con.friend2ID
                friends.add(getUser(id)!!)
            }
        }
        return friends
    }

    fun getNonFriendsOfCurrentUser(): ArrayList<UserModel> {
        if (friendList.isEmpty()) {
            loadFriendConnections()
        }
        val nonFriends = userList.minus(getFriendsOfCurrentUser().toSet()) as ArrayList<UserModel>
        nonFriends.remove(currentUser)
        return nonFriends
    }

    fun createNewFriendConnection(friend1ID: String, friend2ID: String) {
        friendList.add(FriendConnectionModel(friend1ID, friend2ID))
        persistFriendList()
    }

    fun deleteFriendConnection(friendID: String) {
        var i = -1
        friendList.forEachIndexed() { index: Int, con: FriendConnectionModel ->
            if (con.friend1ID == friendID && con.friend2ID == currentUser!!.id || con.friend2ID == friendID && con.friend1ID == currentUser!!.id)
                i = index
        }
        if (i >= 0) friendList.removeAt(i)
        persistFriendList()
    }

    private fun persistFriendList() {
        val settings = getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
        val editor = settings.edit()
        val connectionsAsString: MutableSet<String> = mutableSetOf()
        friendList.forEach { connection: FriendConnectionModel ->
            val userAsString = "${connection.friend1ID}$SPLIT_SYMBOL${connection.friend2ID}"
            connectionsAsString.add(userAsString)
        }
        editor.putStringSet("FriendConnections", connectionsAsString)
        editor.apply()
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "UserInfo"
        private const val SPLIT_SYMBOL = "/-"
    }

}