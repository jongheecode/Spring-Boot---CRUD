package com.example.crud.dto

import jakarta.validation.constraints.NotBlank

data class BoardDto ( //data class -> 데이터를 담는 목적으로 생성

    @field:NotBlank(message = "학교는 필수 항목입니다.") //해당필드가 비어있으면 안됨을 명시
    val school: String,

    @field:NotBlank(message = "학번은 필수 항목입니다.")
    val studentId: String,

    @field:NotBlank(message = "학번은 필수 항목입니다.")
    val name: String,

    @field:NotBlank(message = "학번은 필수 항목입니다.")
    val major:String,

    @field:NotBlank(message = "학번은 필수 항목입니다.")
    val content: String
)