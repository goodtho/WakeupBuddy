package java.com.example.wakeupbuddy.models

import java.util.*

data class AlarmModel(
    var id: String = UUID.randomUUID().toString(),
    var alarmUser: String = "",
    var alarmGroup: String = "",
    var time: String = "",
    var active: Int = 0,
    var name: String = "",
)
