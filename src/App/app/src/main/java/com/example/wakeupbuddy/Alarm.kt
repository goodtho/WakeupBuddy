package com.example.wakeupbuddy

import java.util.*

data class Alarm(var id: UUID = UUID.randomUUID(), var name: String, var date: Calendar)
