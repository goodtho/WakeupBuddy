package com.example.wkbApi.controller

import com.example.wkbApi.MemberRepository
import com.example.wkbApi.models.User_Group
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class GroupMemeberController(private val repository: MemberRepository) {
    @GetMapping("/all")
    fun getAllAlarms(): Iterable<User_Group> {
        val result = repository.findAll()
        println(result)
        return result
    }

    @PostMapping("/")
    fun postAlarm(@RequestBody newMember: User_Group) {
        repository.save(newMember)
    }

//    @GetMapping("/")
//    fun getAlarmById(@RequestParam userId: String, @RequestParam groupId: String): User_Group? {
//        val result = repository.findByMemberID()
//        println(result)
//        return result
//    }
}

