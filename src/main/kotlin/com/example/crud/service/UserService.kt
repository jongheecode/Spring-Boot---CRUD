package com.example.crud.service

import com.example.crud.model.User
import com.example.crud.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
){
    @Transactional
    fun createUser(user: User): User {
        return userRepository.save(user)
    }
}