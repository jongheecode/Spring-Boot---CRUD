package com.example.crud.service

import com.example.crud.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(studentId: String): UserDetails {
        val user = userRepository.findByStudentId(studentId)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $studentId")

        return User(user.studentId, user.password, emptyList())
    }
}