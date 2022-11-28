package java.com.example.wakeupbuddy.models

import java.util.*

data class MessageModel(
    var id: String = UUID.randomUUID().toString(),
    var userId : String = "",
    var groupId : String = "",
    var message : String = "",
    var time : String = ""

)
