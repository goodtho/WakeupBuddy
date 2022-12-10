package com.example.wkbApi.models

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Alarms {
    @Id
    var id: String = UUID.randomUUID().toString()
    var time: String = ""
    var active: Int = 0
    var name: String = ""
    var group_id: String = ""
}
