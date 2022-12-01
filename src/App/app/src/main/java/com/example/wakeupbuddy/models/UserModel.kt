package java.com.example.wakeupbuddy.models

import java.util.*

data class UserModel(
    var id: String = UUID.randomUUID().toString(),
    var username : String = "",
    var password : String = "",
    var birthday : String = "",
    var email : String = ""
)
