package com.example.crud.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    private val key: Key by lazy { Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8)) }
    // JWT 생성 (로그인 성공 시 호출)
    fun createToken(studentId: String): String {
        return Jwts.builder() // <-- 여기를 .builder()로 수정해야 합니다.
            .setSubject(studentId) // 토큰의 주체(사용자 식별자)
            .setIssuedAt(Date(System.currentTimeMillis())) // 토큰 발행 시간
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 만료 시간 (1시간)
            .signWith(key) // 토큰 서명
            .compact() // JWT 생성
    }

    // JWT에서 사용자 ID 추출
    fun extractStudentId(token: String): String {
        return Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token) // .build() 없이 바로 호출
            .body
            .subject
    }

    // JWT 유효성 검증
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token) // .build() 없이 바로 호출
            true
        } catch (e: Exception) {
            false
        }
    }
}