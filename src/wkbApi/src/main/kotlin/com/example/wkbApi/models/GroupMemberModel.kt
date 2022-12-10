package com.example.wkbApi.models

import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.*

//@Embeddable
//class MemberID(
//    private var user_id: String,
//    private var group_id: String) : Serializable
//
//@Entity
//class User_Group(
//    @EmbeddedId
//    var memberID: MemberID
//) : Serializable

@Entity
class User_Group(
    @Id @GeneratedValue private var id: Int,
    @NotNull
    private var user_id: String,
    @NotNull
    private var group_id: String)