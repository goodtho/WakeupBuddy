package com.example.wkbApi.controller

import com.example.wkbApi.AlarmRepository
import com.example.wkbApi.models.Alarms
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/alarms")
class AlarmController(private val repository: AlarmRepository) {

    @GetMapping("/")
    fun getAllAlarms(): Iterable<Alarms> {
        return repository.findAll()
    }

    @PostMapping("/")
    fun createAlarm(@RequestBody newAlarm: Alarms) {
        repository.save(newAlarm)
    }

    @GetMapping("/{id}")
    fun getAlarmById(@PathVariable id: String): Alarms? {
        return repository.findById(id)
    }
}