package com.example.crud.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity //Spring Security 활성화
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder { //비밀번호를 안전하게 암호화
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // CSRF 보호 비활성화
            .formLogin { it.disable() } // 기본 폼 로그인 비활성화
            .httpBasic { it.disable() } // 기본 HTTP Basic 인증 비활성화
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션 사용 안 함

        // API 경로별 접근 권한 설정
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/auth/**").permitAll()
                    // GET 요청과 DELETE 요청 모두 허용
                    .requestMatchers(HttpMethod.GET, "/api/boards/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/boards/{id}").permitAll()
                    .anyRequest().authenticated()
            }

        return http.build()
    }
}