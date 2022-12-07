package com.example.wakeupbuddy.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.wakeupbuddy.MyFriendGroupManager
import com.example.wakeupbuddy.R
import com.example.wakeupbuddy.WakeUpBuddyApp
import com.example.wakeupbuddy.databinding.FragmentMyFriendsBinding
import com.example.wakeupbuddy.activities.AddFriendsActivity
import kotlinx.android.synthetic.main.row_item_friends.view.*
import java.com.example.wakeupbuddy.models.UserModel

class MyFriendsFragment : Fragment() {

    private lateinit var binding: FragmentMyFriendsBinding
    private lateinit var myFriendsManager: MyFriendsManager
    private lateinit var wkbApp: WakeUpBuddyApp
    private lateinit var myFGM: MyFriendGroupManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMyFriendsBinding.inflate(layoutInflater)
        binding.addFriendFab.setOnClickListener {
            val intent =
                Intent(this@MyFriendsFragment.requireContext(), AddFriendsActivity::class.java)
            startActivity(intent)
        }
        wkbApp = context?.applicationContext as WakeUpBuddyApp
        myFGM = wkbApp.getMyFriendGroupManager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myFriendsManager = MyFriendsManager(this@MyFriendsFragment.requireContext())
        binding.myFriendsList.adapter = myFriendsManager
    }

    override fun onResume() {
        super.onResume()
        myFriendsManager.updateFriendList()
        myFriendsManager.notifyDataSetChanged()
    }

    inner class MyFriendsManager(private val context: Context) : BaseAdapter() {

        private var friendList: ArrayList<UserModel>

        init {
            friendList = myFGM.getFriendsOfCurrentUser()
        }

        override fun getCount(): Int {
            return friendList.size
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

            view.name_text.text = friendList[position].name
            view.username_text.text = friendList[position].username
            view.add_friend_button.setImageResource(R.drawable.ic_baseline_person_remove_24)
            view.add_friend_button.setOnClickListener {
                val friendUsername = view.username_text.text.toString()
                val wkbApp = context.applicationContext as WakeUpBuddyApp
                val friend = wkbApp.getUser(friendUsername)
                friendList.remove(friend)
                myFGM.deleteFriendConnection(friend!!.id)
                this.notifyDataSetChanged()
            }

            return view
        }

        fun updateFriendList() {
            friendList = myFGM.getFriendsOfCurrentUser()
        }

    }
}