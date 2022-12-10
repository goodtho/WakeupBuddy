package com.example.wkbApi.controller

import com.example.wkbApi.GroupRepository
import com.example.wkbApi.models.Alarms
import com.example.wkbApi.models.Groups
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
class GroupController(private val repository: GroupRepository) {
    @GetMapping("/")
    fun getAllAlarms(): Iterable<Groups> {
        return repository.findAll()
    }

    @PostMapping("/")
    fun createGroup(@RequestBody newGroup: Groups) {
        repository.save(newGroup)
    }

    @GetMapping("/{id}")
    fun getAlarmById(@PathVariable id: String): Groups? {
        return repository.findById(id)
    }
}