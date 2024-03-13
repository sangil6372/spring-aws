package com.sangil.springaws.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
//
//        http
//                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP 기본 인증 비활성화. 기본적으로 제공되는 로그인 폼 대신 자체 인증 메커니즘을 사용합니다.
//                .csrf(csrf -> csrf.disable()) // CSRF(Cross-Site Request Forgery) 보호 기능 비활성화. REST API에서는 일반적으로 필요하지 않습니다.
//                .cors(Customizer.withDefaults()) // CORS(Cross-Origin Resource Sharing) 설정을 기본 값으로 활성화합니다. 다른 출처의 리소스 요청을 허용합니다.
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                ) // 모든 요청에 대해 인증된 사용자만 접근을 허용합니다. 인증되지 않은 사용자의 요청은 거부됩니다.
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter 실행 전에 커스텀 JWT 필터를 추가합니다. JWT 토큰 검증을 통한 인증 처리를 수행합니다.
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
//                ); // 인증 실패나 접근 권한이 없을 경우 처리할 핸들러를 지정합니다. Bearer 토큰 인증 실패 시 사용될 EntryPoint와 접근 거부 핸들러를 설정합니다.
//        return http.build(); // 구성된 HttpSecurity 객체를 사용하여 SecurityFilterChain을 생성하고 반환합니다.
//
//    }

}
