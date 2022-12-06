package java.com.example.wakeupbuddy.models

import java.util.*

data class UserModel(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var username: String = "",
    var password: String = "",
    var email: String = ""
)
