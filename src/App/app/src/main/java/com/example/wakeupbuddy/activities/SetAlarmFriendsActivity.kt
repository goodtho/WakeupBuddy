package com.example.wakeupbuddy.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeupbuddy.MyFriendGroupManager
import com.example.wakeupbuddy.R
import com.example.wakeupbuddy.WakeUpBuddyApp
import java.util.*
import com.example.wakeupbuddy.databinding.ActivitySetAlarmFriendsBinding
import kotlinx.android.synthetic.main.row_item_add_friends_alarm.view.*
import kotlinx.android.synthetic.main.row_item_friends.view.*
import kotlinx.android.synthetic.main.row_item_friends.view.name_text
import kotlinx.android.synthetic.main.row_item_friends.view.username_text
import kotlinx.android.synthetic.main.row_item_with_button.view.*
import java.com.example.wakeupbuddy.models.UserModel

class SetAlarmFriendsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetAlarmFriendsBinding
    private lateinit var myAFM: MyAddFriendsManager
    private lateinit var myFGM: MyFriendGroupManager
    private val newGroup: ArrayList<UserModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addFriendsList = binding.addFriendList
        myAFM = MyAddFriendsManager(this@SetAlarmFriendsActivity)
        addFriendsList.adapter = myAFM

        val wkbApp = applicationContext as WakeUpBuddyApp
        if (!wkbApp.myFriendGroupManagerIsInitialized()) {
            wkbApp.initializeMyFriendGroupManager(this@SetAlarmFriendsActivity.applicationContext)
        }
        myFGM = wkbApp.getMyFriendGroupManager()

        //todo get user by username, add him to a group
        //todo persists group, connect group to alarm, persist alarm

        binding.setAlarmButton.setOnClickListener {
            //create alarm with data from intent (alarm name and time) + group/friends
            val group = myFGM.createGroup()
            myFGM.addUserToGroup(wkbApp.getCurrentUser()!!, group)
            newGroup.forEach { user: UserModel ->
                myFGM.addUserToGroup(user, group)
            }

            val extras: Bundle? = intent.extras
            val alarmName = extras?.getString("Name")
            val alarmTimeAsStrings = extras?.getString("Time")
            val hoursMinutes = alarmTimeAsStrings!!.split(':')
            val hours = hoursMinutes[0].toInt()
            val minutes = hoursMinutes[1].toInt()

            wkbApp.getMyAlarmManager().createAlarm(alarmName!!, hours, minutes, group.id)
            wkbApp.getMyAlarmManager().notifyDataSetChanged()
            finish()
        }

        binding.nextButton.setOnClickListener {
            //create alarm with data from intent (alarm name and time) + group/friends
            val group = myFGM.createGroup()
            myFGM.addUserToGroup(wkbApp.getCurrentUser()!!, group)
            newGroup.forEach { user: UserModel ->
                myFGM.addUserToGroup(user, group)
            }

            val extras: Bundle? = intent.extras
            val alarmName = extras?.getString("Name")
            val alarmTimeAsStrings = extras?.getString("Time")
            val hoursMinutes = alarmTimeAsStrings!!.split(':')
            val hours = hoursMinutes[0].toInt()
            val minutes = hoursMinutes[1].toInt()

            wkbApp.getMyAlarmManager().createAlarm(alarmName!!, hours, minutes, group.id)
            wkbApp.getMyAlarmManager().notifyDataSetChanged()

            val intent = Intent(this@SetAlarmFriendsActivity, SetAlarmDevicesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    inner class MyAddFriendsManager(private val context: Context) : BaseAdapter() {
        private var myFriendList: ArrayList<UserModel>

        init {
            val wkbApp = context.applicationContext as WakeUpBuddyApp
            myFriendList = wkbApp.getMyFriendGroupManager().getFriendsOfCurrentUser()
        }

        override fun getCount(): Int {
            return myFriendList.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.row_item_add_friends_alarm, parent, false)

            view.name_text.text = myFriendList[position].name
            view.username_text.text = myFriendList[position].username

            view.add_friend_checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    newGroup.add(myFriendList[position])
                } else {
                    newGroup.remove(myFriendList[position])
                }
            }

            return view
        }

    }
}