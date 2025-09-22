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

@RestController
@RequestMapping("api/comments")
class CommentController(private val commentService: CommentService){

    //댓글 생성
    @PostMapping("/boards/{boardId}/users/{userId}")
    fun createComment(
        @PathVariable boardId: Long,
        @PathVariable userId: Long,
        @Valid @RequestBody commentDto: CommentDto
    ): ResponseEntity<Comment>{
        val newComment=commentService.createComment(boardId,userId,commentDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment)
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @Valid @RequestBody commentDto: CommentDto
    ): ResponseEntity<Comment>{
        val updateComment=commentService.updateComment(commentId,commentDto)
        return ResponseEntity.ok(updateComment)
    }
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId:Long,
    ): ResponseEntity<Void>{
        val deleteComment=commentService.deleteComment(commentId)
        return ResponseEntity.noContent().build()
    }
}