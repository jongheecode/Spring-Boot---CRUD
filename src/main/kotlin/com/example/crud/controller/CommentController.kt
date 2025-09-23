package com.example.crud.controller

import com.example.crud.dto.CommentDto
import com.example.crud.model.Comment
import com.example.crud.service.CommentService
import jakarta.persistence.Id
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController // 이 클래스가 RESTful API를 처리하는 컨트롤러임을 나타냅니다.
@RequestMapping("api/comments") // 이 컨트롤러의 모든 엔드포인트는 /api/comments 로 시작합니다.
class CommentController(private val commentService: CommentService){

    //댓글 생성
    @PostMapping("/boards/{boardId}/users/{userId}") // POST 요청을 매핑합니다. URL에 경로 변수를 포함합니다.
    fun createComment(
        @PathVariable boardId: Long, // URL 경로의 {boardId} 값을 Long 타입 변수로 받습니다.
        @PathVariable userId: Long, // URL 경로의 {userId} 값을 Long 타입 변수로 받습니다.
        @Valid @RequestBody commentDto: CommentDto // HTTP 요청 본문(Body)을 CommentDto 객체로 변환합니다.
    ): ResponseEntity<Comment>{ // HTTP 응답의 상태 코드, 헤더, 본문을 담는 객체입니다.
        val newComment=commentService.createComment(boardId,userId,commentDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment)
    }

    //댓글 수정
    @PutMapping("/{commentId}") // PUT 요청을 매핑합니다. URL에 {commentId}를 포함합니다.
    fun updateComment(
        @PathVariable commentId: Long,
        @Valid @RequestBody commentDto: CommentDto
    ): ResponseEntity<Comment>{
        val updateComment=commentService.updateComment(commentId,commentDto)
        return ResponseEntity.ok(updateComment)
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}") // DELETE 요청을 매핑합니다. URL에 {commentId}를 포함합니다.
    fun deleteComment(
        @PathVariable commentId:Long,
    ): ResponseEntity<Void>{
        val deleteComment=commentService.deleteComment(commentId)
        return ResponseEntity.noContent().build()
    }
}