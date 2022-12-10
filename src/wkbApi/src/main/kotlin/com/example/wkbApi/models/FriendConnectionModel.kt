package com.example.wkbApi.models

import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

//@Embeddable
//class FriendID(
//    private var user1_id: String,
//    private var user2_id: String) : Serializable
//
//@Entity
//class User_User(
//    @EmbeddedId
//    var friendID: FriendID
//) : Serializable

@Entity
class User_User(
    @Id @GeneratedValue private var id: Int,
    @NotNull
    private var user1_id: String,
    @NotNull
    private var user2_id: String)