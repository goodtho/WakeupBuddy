package com.example.wakeupbuddy

import android.content.Context
import com.example.wakeupbuddy.models.FriendConnectionModel
import java.com.example.wakeupbuddy.models.GroupModel
import java.com.example.wakeupbuddy.models.GroupMemberModel
import java.com.example.wakeupbuddy.models.UserModel

class MyFriendGroupManager(context: Context) {
    private var friendList: ArrayList<FriendConnectionModel> = ArrayList()
    private var groupList: ArrayList<GroupModel> = ArrayList()
    private var groupMembershipsList: ArrayList<GroupMemberModel> = ArrayList()
    private var wkbApp: WakeUpBuddyApp

    init {
        wkbApp = context.applicationContext as WakeUpBuddyApp
    }

    private fun loadFriendConnections() {
        val settings = wkbApp.getSharedPreferences(WakeUpBuddyApp.SHARED_PREFERENCES_NAME, 0)
        val friendConnectionsAsStrings: Set<String>? =
            settings.getStringSet("FriendConnections", null)
        friendConnectionsAsStrings?.forEach { connection: String ->
            val connectionData = connection.split(WakeUpBuddyApp.SPLIT_SYMBOL)
            friendList.add(FriendConnectionModel(connectionData[0], connectionData[1]))
        }
    }

    fun getFriendsOfCurrentUser(): ArrayList<UserModel> {
        if (friendList.isEmpty()) {
            loadFriendConnections()
        }

        val friends = ArrayList<UserModel>()
        friendList.forEach { con: FriendConnectionModel ->
            if (con.friend1ID == wkbApp.getCurrentUser()?.id ||
                con.friend2ID == wkbApp.getCurrentUser()?.id
            ) {
                val id =
                    if (con.friend1ID != wkbApp.getCurrentUser()?.id) con.friend1ID else con.friend2ID
                friends.add(wkbApp.getUser(id)!!)
            }
        }
        return friends
    }

    fun getNonFriendsOfCurrentUser(): ArrayList<UserModel> {
        if (friendList.isEmpty()) {
            loadFriendConnections()
        }
        val nonFriends =
            wkbApp.getUserList().minus(getFriendsOfCurrentUser().toSet()) as ArrayList<UserModel>
        nonFriends.remove(wkbApp.getCurrentUser())
        return nonFriends
    }

    fun createNewFriendConnection(friend1ID: String, friend2ID: String) {
        friendList.add(FriendConnectionModel(friend1ID, friend2ID))
        persistFriendList()
    }

    fun deleteFriendConnection(friendID: String) {
        var i = -1
        friendList.forEachIndexed() { index: Int, con: FriendConnectionModel ->
            if (con.friend1ID == friendID && con.friend2ID == wkbApp.getCurrentUser()!!.id ||
                con.friend2ID == friendID && con.friend1ID == wkbApp.getCurrentUser()!!.id
            )
                i = index
        }
        if (i >= 0) friendList.removeAt(i)
        persistFriendList()
    }

    private fun persistFriendList() {
        val settings = wkbApp.getSharedPreferences(WakeUpBuddyApp.SHARED_PREFERENCES_NAME, 0)
        val editor = settings.edit()
        val connectionsAsString: MutableSet<String> = mutableSetOf()
        friendList.forEach { connection: FriendConnectionModel ->
            val userAsString =
                "${connection.friend1ID}${WakeUpBuddyApp.SPLIT_SYMBOL}${connection.friend2ID}"
            connectionsAsString.add(userAsString)
        }
        editor.putStringSet("FriendConnections", connectionsAsString)
        editor.apply()
    }

    private fun loadGroups() {
        val settings = wkbApp.getSharedPreferences(WakeUpBuddyApp.SHARED_PREFERENCES_NAME, 0)
        val groupsAsStrings: Set<String>? = settings.getStringSet("Groups", null)
        groupsAsStrings?.forEach { group: String ->
            val groupData = group.split(WakeUpBuddyApp.SPLIT_SYMBOL)
            groupList.add(GroupModel(groupData[0], groupData[1], groupData[2]))
        }

    }

    private fun persistGroups() {
        val settings = wkbApp.getSharedPreferences(WakeUpBuddyApp.SHARED_PREFERENCES_NAME, 0)
        val editor = settings.edit()
        val groupsAsStrings: MutableSet<String> = mutableSetOf()
        groupList.forEach { group: GroupModel ->
            val groupAsString =
                "${group.id}${WakeUpBuddyApp.SPLIT_SYMBOL}${group.groupAdmin}" +
                        "${WakeUpBuddyApp.SPLIT_SYMBOL}${group.name}"
            groupsAsStrings.add(groupAsString)
        }
        editor.putStringSet("Groups", groupsAsStrings)
        editor.apply()
    }

    fun getMembersOfGroup(groupID: String): ArrayList<UserModel> {
        val members = ArrayList<UserModel>()
        groupMembershipsList.forEach { membership: GroupMemberModel ->
            if (membership.groupId == groupID) {
                wkbApp.getUser(membership.userId)?.let { members.add(it) }
            }
        }
        return members
    }

    fun createGroup(): GroupModel {
        //todo implement setting a name for the group
        if (groupList.isEmpty()) {
            loadGroups()
        }
        val group = GroupModel()
        groupList.add(group)
        persistGroups()
        return group
    }

    fun addUserToGroup(user: UserModel, group: GroupModel) {
        if (groupMembershipsList.isEmpty()) {
            loadGroupMemberships()
        }
        val membership = GroupMemberModel(user.id, group.id)
        groupMembershipsList.add(membership)
        persistGroupMemberships()
    }

//    fun removeUserFromGroup(user: UserModel, group: GroupModel) {
//
//    }

    private fun loadGroupMemberships() {
        val settings = wkbApp.getSharedPreferences(WakeUpBuddyApp.SHARED_PREFERENCES_NAME, 0)
        val membershipsAsStrings = settings.getStringSet("Memberships", null)
        membershipsAsStrings?.forEach { membership: String ->
            val membershipData = membership.split(WakeUpBuddyApp.SPLIT_SYMBOL)
            groupMembershipsList.add(GroupMemberModel(membershipData[0], membershipData[1]))
        }

    }

    private fun persistGroupMemberships() {
        val settings = wkbApp.getSharedPreferences(WakeUpBuddyApp.SHARED_PREFERENCES_NAME, 0)
        val editor = settings.edit()
        val membershipsAsStrings: MutableSet<String> = mutableSetOf()
        groupMembershipsList.forEach { membership: GroupMemberModel ->
            val membershipAsString =
                "${membership.userId}${WakeUpBuddyApp.SPLIT_SYMBOL}${membership.groupId}"
            membershipsAsStrings.add(membershipAsString)
        }
        editor.putStringSet("Memberships", membershipsAsStrings)
        editor.apply()
    }
}