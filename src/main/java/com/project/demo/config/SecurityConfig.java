package com.project.demo.config;

import com.project.demo.api.dto.UserLoginResponse;
import com.project.demo.jwt.JwtAuthenticationFilter;
import com.project.demo.jwt.JwtTokenProvider;
import com.project.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic((basic -> basic.disable()))
                //서버에 세션 저장 안함, 매 요청마다 JWT 필요
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/signup",
                                "/api/users/login",
                                "/api/users/reissue"
                        ).permitAll()
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                "/api/report/**"
                        ).hasRole("USER") //승인된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )

                // JWT 필터 추가
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, userRepository),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
