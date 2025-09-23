package com.example.crud.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        http
            .authorizeHttpRequests { auth ->
                auth
                    // 정적 파일 및 메인 페이지는 모두에게 허용
                    .requestMatchers("/", "/index.html", "/styles.css", "/script.js", "/favicon.ico").permitAll()
                    // 로그인/회원가입 API는 모두에게 허용
                    .requestMatchers("/api/auth/**").permitAll()
                    // 게시물 조회는 모두에게 허용
                    .requestMatchers(HttpMethod.GET, "/api/boards/**").permitAll()
                    // 나머지 모든 요청은 인증 필요
                    .anyRequest().authenticated()
            }
            .exceptionHandling { it.authenticationEntryPoint(null) }

        return http.build()
    }
}