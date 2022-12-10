package java.com.example.wakeupbuddy.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class AlarmModel(
    @SerializedName("id")
    var id: String = UUID.randomUUID().toString(),
    @SerializedName("time")
    var time: String = "",
    @SerializedName("active")
    var active: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("group_id")
    var groupID: String = "",
)
