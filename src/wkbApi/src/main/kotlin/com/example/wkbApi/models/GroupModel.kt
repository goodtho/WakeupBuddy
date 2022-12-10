package com.example.wkbApi.models

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Groups {
    @Id
    var id: String = UUID.randomUUID().toString()
    var name: String = ""
}