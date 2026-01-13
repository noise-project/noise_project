package com.project.demo.jwt;

import com.project.demo.domain.user.Status;
import com.project.demo.domain.user.User;
import com.project.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1. Authorization 헤더 없음
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 토큰 추출
        String token = authHeader.substring(7);

        // 3. 토큰 검증
        if (!jwtTokenProvider.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtTokenProvider.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 4. 권한 결정
        String role = claims.get("role", String.class);
        String status = claims.get("status", String.class);
        Long userId = Long.valueOf(claims.getSubject());

        SimpleGrantedAuthority authority;

        if("ADMIN".equals(role)) {
            authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        } else if ("APPROVED".equals(status)) {
            authority = new SimpleGrantedAuthority("ROLE_USER");
        } else {
            authority = new SimpleGrantedAuthority("ROLE_PENDING");
        }

        // 5. 인증 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of(authority)
                );

        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // 6. SecurityContext 등록
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
