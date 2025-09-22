package com.example.crud.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name="comments") //댓글 정보를 담는 테이블
data class Comment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long=0,
    @Column(nullable = false)
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_Id", nullable = false) //board_id를 외래키로 설정
    val board: Board,
    @ManyToOne(fetch = FetchType.LAZY) //다대일 연결 : 여러개의 댓글이 하나의 게시물 또는 사용자에게 속할 수 있음
    //LAZY : 이 객체를 실제로 사용할 때만 데이터를 가져와 성능 최적화
    @JoinColumn(name="user_Id", nullable = false) //user_Id를 외래키로 설정
    val user: User,
    @Column(nullable = false)
    val createdAt: LocalDateTime= LocalDateTime.now(),
    @Column(nullable = false)
    var updatedAt: LocalDateTime= LocalDateTime.now()

)