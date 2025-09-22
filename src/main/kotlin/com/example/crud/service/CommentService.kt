package com.example.crud.service

import com.example.crud.dto.CommentDto
import com.example.crud.model.Board
import com.example.crud.model.Comment
import com.example.crud.model.User
import com.example.crud.repository.BoardRepository
import com.example.crud.repository.CommentRepository
import com.example.crud.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
){
    @Transactional
    //게시물Id와 유저Id로 각각의 엔티티를 찾아온 후 새로운 댓글 엔티티 생성 후 저장
    fun createComment(boardId: Long,userId:Long,commentDto: CommentDto): Comment{
        val board: Board=boardRepository.findByIdOrNull(boardId)
            ?:throw EntityNotFoundException("게시물을 찾을 수 없습니다:${boardId}")
        val user: User=userRepository.findByIdOrNull(userId )
            ?:throw EntityNotFoundException("유저를 찾을 수 없습니다:${userId}")

        val comment= Comment(
            content = commentDto.content,
            board = board,
            user=user
        )
        return commentRepository.save(comment)
    }

    @Transactional
    fun updateComment(commentId:Long,commentDto: CommentDto): Comment{
        val comment: Comment=commentRepository.findByIdOrNull(commentId)
            ?:throw EntityNotFoundException("댓글을 찾을 수 없습니다:${commentId}")
        comment.content=commentDto.content
        return commentRepository.save(comment)
    }

    @Transactional
    fun deleteComment(commentId: Long) {
        val comment: Comment = commentRepository.findByIdOrNull(commentId)
            ?: throw EntityNotFoundException("댓글을 찾을 수 없습니다: $commentId")

        commentRepository.delete(comment)
    }

}