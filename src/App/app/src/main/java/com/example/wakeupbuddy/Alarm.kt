package com.example.wakeupbuddy

import java.util.*

data class Alarm(
    var name: String,
    var date: Calendar,
    var isActive: Boolean,
    var id: UUID = UUID.randomUUID()
)
