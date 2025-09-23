package com.example.crud.controller

import com.example.crud.model.User
import com.example.crud.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService){

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User>{
        val createUser=userService.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser)
    }
}