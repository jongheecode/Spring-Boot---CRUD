package com.example.crud.service
import com.example.crud.dto.BoardDto
import com.example.crud.model.Board
import com.example.crud.repository.BoardRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BoardService(private val boardRepository: BoardRepository) {

    // 게시물 전체 조회
    fun getAllBoards(): List<Board> {
        return boardRepository.findAll()
    }

    // 게시물 ID로 조회
    fun getBoardById(id: Long): Board {
        return boardRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("게시물을 찾을 수 없습니다: $id")
    }

    // 게시물 생성
    @Transactional
    fun createBoard(boardDto: BoardDto): Board {
        val board = Board(
            school = boardDto.school,
            studentId = boardDto.studentId,
            name = boardDto.name,
            major = boardDto.major,
            content = boardDto.content
        )
        return boardRepository.save(board)
    }

    // 게시물 수정
    @Transactional
    fun updateBoard(id: Long, boardDto: BoardDto): Board {
        val board = boardRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("수정할 게시물을 찾을 수 없습니다: $id")

        board.school = boardDto.school
        board.studentId = boardDto.studentId
        board.name = boardDto.name
        board.major = boardDto.major
        board.content = boardDto.content
        board.updatedAt = LocalDateTime.now()

        return boardRepository.save(board)
    }

    // 게시물 삭제
    @Transactional
    fun deleteBoard(id: Long) {
        val board = boardRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException("삭제할 게시물을 찾을 수 없습니다: $id")

        boardRepository.delete(board)
    }

    fun getBoardByStudentId(studentId: String): List<Board>{
        return boardRepository.findByStudentId(studentId)
    }
}