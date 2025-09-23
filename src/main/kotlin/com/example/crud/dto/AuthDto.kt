package com.example.crud.dto

import jakarta.validation.constraints.NotBlank

data class AuthDto(

    @field:NotBlank(message = "학번은 필수 항목입니다.")
    val studentId: String,
    @field:NotBlank(message = "비밀번호는 필수 항목입니다.")
    val password: String
)