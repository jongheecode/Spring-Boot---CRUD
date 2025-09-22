package com.example.crud.controller

import com.example.crud.dto.BoardDto
import com.example.crud.model.Board
import com.example.crud.service.BoardService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController //RestfullAPI 제공하는 함수임을 선언 -> JSON 형식으로 자동 변환 <->Controller
@RequestMapping("/api/boards") //이 컨트롤러의 API 엔드포인트는 /api/boards로 시작
class BoardController(private val boardService: BoardService){

    //1.게시물 전체 조회
    @GetMapping
    fun getAllBoards(): ResponseEntity<List<Board>>{
        val boards=boardService.getAllBoards()
        return ResponseEntity.ok(boards)
    }

    //2.id로 게시물 조회
    fun getBoardById(@PathVariable id: Long): ResponseEntity<Board>{ //PathVariable -> url 경로의 id를 파라미터로 받음
        val board=boardService.getBoardById(id)
        return ResponseEntity.ok(board)
    }

    //3.게시물 생성
    @PostMapping
    fun createBoard(@Valid @RequestBody boardDto: BoardDto): ResponseEntity<Board>{
        //RequestBody -> HTTP 요청의 body에 담긴 데이터를 BoardDto 객체로 변환
        //Valid -> boardDto에 정의한 @NotBlank 같은 유효성 검사를 자동으로 실행
    val newBoard=boardService.createBoard(boardDto)
    return ResponseEntity.status(HttpStatus.CREATED).body(newBoard)
    }

    //4.게시물 수정

    @PutMapping("/{id}")
    fun updateBoard(@PathVariable id:Long,@Valid @RequestBody boardDto: BoardDto): ResponseEntity<Board>{
        val updateBoard=boardService.updateBoard(id,boardDto)
        return ResponseEntity.ok(updateBoard)
    }

    //5.게시물 삭제

    @DeleteMapping
    fun deleteBoard(@PathVariable id: Long): ResponseEntity<Void>{
        boardService.deleteBoard(id)
        return ResponseEntity.noContent().build()
    }
}