package java.com.example.wakeupbuddy.models

import java.util.*

data class GroupModel(
    var id: String = UUID.randomUUID().toString(),
    var groupAdmin: String = "",
    var name: String = ""
)
