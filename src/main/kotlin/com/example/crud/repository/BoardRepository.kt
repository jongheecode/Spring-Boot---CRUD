package com.example.crud.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository //이 클래스가 데이터 접근 계층의 클래스임을 표시
interface BoardRepository : JpaRepository<Board, Long>/*
    스프링 데이터 JPA의 핵심 인터페이스
    <Board, Long>은 각각 엔티티 클래스와 기본 키(Primary Key)의 타입을 의미한다.
    이 인터페이스를 상속받는 것만으로 save(), findById(), findAll(), deleteById()와 같은 메서드들을 사용할 수 있게 된다.*/