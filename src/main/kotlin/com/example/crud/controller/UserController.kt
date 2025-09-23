package com.example.crud.controller

import com.example.crud.dto.AuthDto
import com.example.crud.model.User
import com.example.crud.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController // 1. REST API 컨트롤러임을 나타냅니다.
@RequestMapping("/api/users") // 2. 이 컨트롤러의 모든 API 엔드포인트는 "/api/users"로 시작합니다.
class UserController(private val userService: UserService){ // 3. 생성자 주입으로 UserService 객체를 받습니다.

    @PostMapping // 4. POST HTTP 메서드에 매핑합니다. (회원가입/생성)
    fun createUser(@RequestBody authDto: AuthDto): ResponseEntity<User>{
        // 5. 요청 본문(JSON)을 User 객체로 자동 변환합니다.
        val createUser=userService.createUser(authDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser)
        // 6. 상태 코드 201 (CREATED)과 함께 생성된 사용자 정보를 응답 본문으로 반환합니다.
    }

    @DeleteMapping("/{studentId}") // 7. DELETE HTTP 메서드에 매핑합니다. (사용자 삭제)
    fun deleteUser(@PathVariable studentId: String): ResponseEntity<Void>{
        // 8. URL 경로의 변수(studentId)를 메서드 매개변수로 가져옵니다.
        userService.deleteUser(studentId)
        return ResponseEntity.noContent().build()
        // 9. 상태 코드 204 (No Content)를 반환합니다.
    }
}