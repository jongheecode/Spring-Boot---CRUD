package com.example.crud.controller

import com.example.crud.dto.AuthDto
import com.example.crud.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthApiController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody authDto: AuthDto): ResponseEntity<Any> {
        authService.register(authDto)
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공")
    }
}