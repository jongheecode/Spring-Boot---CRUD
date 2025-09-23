package com.example.crud.service

import com.example.crud.dto.AuthDto
import com.example.crud.model.User
import com.example.crud.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
){
    @Transactional
    fun createUser(authDto: AuthDto): User {
        val encryptedPassword=passwordEncoder.encode(authDto.password)

        val newUser= User(
            studentId = authDto.studentId,
            school = authDto.school,
            password = encryptedPassword,
            name = authDto.name,
            major = authDto.major
        )
        return userRepository.save(newUser)
    }

    fun deleteUser(studentId: String){
        val user: User=userRepository.findByStudentId(studentId)
            ?: throw EntityNotFoundException("유저를 찾을 수 없습니다.")
        userRepository.delete(user)
    }
}