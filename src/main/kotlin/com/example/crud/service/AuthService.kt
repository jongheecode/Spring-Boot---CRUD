package com.example.crud.service

import com.example.crud.dto.AuthDto
import com.example.crud.model.User
import com.example.crud.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun register(authDto: AuthDto): User {
        // 비밀번호 암호화
        val encryptedPassword = passwordEncoder.encode(authDto.password)

        // 사용자 객체 생성 (나머지 정보는 DTO에 없음)
        val newUser = User(
            studentId = authDto.studentId,
            password = encryptedPassword,
            name = authDto.name, // 임시값
            major = authDto.major, // 임시값
            school = authDto.school // 임시값
        )
        return userRepository.save(newUser)
    }

    fun authenticate(authDto: AuthDto): User? {
        val user = userRepository.findByStudentId(authDto.studentId)
        if (user != null && passwordEncoder.matches(authDto.password, user.password)) {
            return user
        }
        return null
    }
}