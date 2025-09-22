package com.example.crud.dto

import jakarta.validation.constraints.NotBlank

data class CommentDto( //댓글 생성 및 수정 시 클라이언트로부터 받을 데이터
    @field:NotBlank(message = "내용은 필수 항목입니다.")
    val content: String
)