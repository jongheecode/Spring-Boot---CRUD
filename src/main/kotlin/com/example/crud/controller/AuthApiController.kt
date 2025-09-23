package com.example.crud.controller

import com.example.crud.dto.AuthDto
import com.example.crud.service.AuthService
import com.example.crud.util.JwtUtil
import jakarta.validation.Valid
import org.springframework.boot.context.properties.bind.Bindable.mapOf
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.collections.mapOf

@RestController
@RequestMapping("/api/auth")
class AuthApiController(
    private val authService: AuthService,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody authDto: AuthDto): ResponseEntity<Any> {
        authService.register(authDto)
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공")
    }

    fun login(@Valid @RequestBody authDto: AuthDto): ResponseEntity<Any>{
        val user = authService.authenticate(authDto)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패")

        val token = JwtUtil.createToken(user.studentId) // JWT 토큰 생성
        return ResponseEntity.ok(mapOf("token",token)) // 토큰 반환
    }
}
