package com.example.crud.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="users") //유저의 정보를 담는 테이블
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long=0,

    @Column(unique = true, nullable = false )
    val studentId: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val major: String,
    @Column(nullable = false)
    val school: String
)