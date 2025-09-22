package com.example.crud.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity //이 엔티티가 DB와 연결됨을 표시
@Table(name="board")    //이 엔티티가 board라는 테이블과 연결됨을 표시
data class Board(

    @Id //id를 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id값이 자동으로 생성되고 기본키 생성을 DB에 위임
    val id:Long=0,

    val school: String,
    val studentId: String,
    val name: String,
    val major: String,
    val content:String,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime= LocalDateTime.now(),

    @Column(nullable=false)
    val updatedAt: LocalDateTime= LocalDateTime.now()
)