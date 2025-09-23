package com.example.crud.dto

import jakarta.validation.constraints.NotBlank

data class AuthDto(

    @field:NotBlank(message = "학번은 필수 항목입니다.")
    val studentId: String,
    @field:NotBlank(message = "비밀번호는 필수 항목입니다.")
    val password: String,
    @field:NotBlank(message = "학교명은 필수 항목입니다.")
    val school: String,
    @field:NotBlank(message = "학과명은 필수 항목입니다.")
    val major: String,
    @field:NotBlank(message = "이름은 필수 항목입니다.")
    val name: String
)