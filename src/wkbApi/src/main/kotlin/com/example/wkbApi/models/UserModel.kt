package com.example.wkbApi.models

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Users {
    @Id
    var id: String = UUID.randomUUID().toString()
    var name: String = ""
    var username: String = ""
    var password: String = ""
    var email: String = ""
}