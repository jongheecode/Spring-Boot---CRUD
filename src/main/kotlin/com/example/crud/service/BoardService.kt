package com.example.crud.service

import com.example.crud.model.Board
import com.example.crud.repository.BoardRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull

class BoardService(private val BoardRepository: BoardRepository){

    //게시물 전체 조회
    fun getAllBoards(): List<Board>{
        return BoardRepository.findAll()
    }

    //게시물 ID로 조회
    fun getBoardById(id: Long): Board {
        return BoardRepository.findByIdOrNull(id)
            ?:throw EntityNotFoundException("게시물을 찾을 수 없습니다: $id")
    }
}