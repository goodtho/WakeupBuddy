package com.example.wkbApi.controller

import com.example.wkbApi.UserRepository
import com.example.wkbApi.models.Users
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val repository: UserRepository) {
    @GetMapping("/")
    fun getAllUser(): Iterable<Users> {
        return repository.findAll()
    }

    @PostMapping("/")
    fun createUser(@RequestBody newUser: Users) {
        repository.save(newUser)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): Users? {
        return repository.findById(id)
    }
}
