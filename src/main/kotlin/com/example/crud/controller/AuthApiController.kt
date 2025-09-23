package com.example.crud.controller

import com.example.crud.dto.AuthDto
import com.example.crud.dto.LoginDto
import com.example.crud.service.AuthService
import com.example.crud.util.JwtUtil
import jakarta.validation.Valid
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

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginDto: LoginDto): ResponseEntity<Any>{
        val user = authService.authenticate(loginDto.studentId,loginDto.password)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패")

        val token = jwtUtil.createToken(user.studentId) // JWT 토큰 생성
        return ResponseEntity.ok(mapOf("token" to token)) // 토큰 반환
    }
}
