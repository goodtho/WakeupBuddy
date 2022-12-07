package com.example.wakeupbuddy.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.wakeupbuddy.MyFriendGroupManager
import com.example.wakeupbuddy.R
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.databinding.ActivityAddFriendBinding
import kotlinx.android.synthetic.main.row_item_friends.view.*
import java.com.example.wakeupbuddy.models.UserModel

class AddFriendsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFriendBinding
    private lateinit var myNonFriendsManager: MyNonFriendsManager
    private lateinit var myFGM: MyFriendGroupManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wkbApp = applicationContext as WakeUpBuddyApp
        if (!wkbApp.myFriendGroupManagerIsInitialized()) {
            wkbApp.initializeMyFriendGroupManager(this@AddFriendsActivity.applicationContext)
        }
        myFGM = wkbApp.getMyFriendGroupManager()

        myNonFriendsManager = MyNonFriendsManager(this@AddFriendsActivity)
        binding.contactList.adapter = myNonFriendsManager

        binding.finishAddFriendsButton.setOnClickListener {
            finish()
        }
    }

    inner class MyNonFriendsManager(private val context: Context) : BaseAdapter() {
        private var nonFriendsList: ArrayList<UserModel> = myFGM.getNonFriendsOfCurrentUser()

        override fun getCount(): Int {
            return nonFriendsList.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.row_item_friends, parent, false)
            view.name_text.text = nonFriendsList[position].name
            view.username_text.text = nonFriendsList[position].username
            view.add_friend_button.setOnClickListener {
                val wkbApp = context.applicationContext as WakeUpBuddyApp
                val curUser = wkbApp.getCurrentUser()
                val newFriend = wkbApp.getUser(nonFriendsList[position].username)
                myFGM.createNewFriendConnection(curUser!!.id, newFriend!!.id)

                view.add_friend_button.setImageResource(R.drawable.ic_baseline_check_circle_24)
                view.add_friend_button.isClickable = false
            }
            return view
        }

    }
}