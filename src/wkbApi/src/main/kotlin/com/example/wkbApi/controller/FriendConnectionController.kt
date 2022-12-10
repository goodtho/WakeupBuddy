package com.example.wkbApi.controller

import com.example.wkbApi.FriendRepository
import com.example.wkbApi.models.User_User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/friends")
class FriendConnectionController(private val repository: FriendRepository) {
    @GetMapping("/")
    fun getAllAlarms(): Iterable<User_User> {
        val result = repository.findAll()
        println(result)
        return result
    }

    @PostMapping("/")
    fun postAlarm(@RequestBody newFriend: User_User) {
        repository.save(newFriend)
    }

//    @GetMapping("/{id}")
//    fun getAlarmById(@ParameterName id: String): User_User? {
//        val result = repository.findById(id)
//        println(result)
//        return result
//    }
}