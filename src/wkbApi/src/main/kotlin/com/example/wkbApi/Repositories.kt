package com.example.wkbApi

import com.example.wkbApi.models.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface AlarmRepository : CrudRepository<Alarms, Long> {
    fun findById(id: String): Alarms?
}

interface UserRepository : CrudRepository<Users, Long> {
    fun findById(id: String): Users?
}

interface GroupRepository : CrudRepository<Groups, Long> {
    fun findById(id: String): Groups?
}

interface FriendRepository : JpaRepository<User_User, Long> {
//    fun findByFriendID(friendID: FriendID): User_User?
}

interface MemberRepository : JpaRepository<User_Group, Long> {
//    fun findByMemberID(memberID: MemberID): User_Group?
}