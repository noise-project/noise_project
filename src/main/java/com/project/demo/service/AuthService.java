package com.project.demo.service;

import com.project.demo.api.dto.LoginResult;
import com.project.demo.api.dto.UserLoginRequest;
import com.project.demo.api.dto.UserLoginResponse;
import com.project.demo.domain.user.Status;
import com.project.demo.domain.user.User;
import com.project.demo.jwt.JwtTokenProvider;
import com.project.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResult login(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 승인 거절
        if (user.getStatus() == Status.REJECTED) {
            throw new IllegalArgumentException("가입이 거절된 계정입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(
                user.getUserId(),
                user.getEmail(),
                user.getRole().name(),
                user.getStatus().name()
        );

        String refreshToken = jwtTokenProvider.createRefreshToken();
        // refreshToken DB 저장
        user.updateRefreshToken(refreshToken);

        UserLoginResponse response = new UserLoginResponse(
                user.getUserId(),
                user.getEmail(),
                accessToken,
                user.getStatus().name()
        );

        return new LoginResult(response,refreshToken);
    }

    @Transactional
    public String reissueAccessToken(String refreshToken) {

        if(!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("토큰 주인 없음"));

        return jwtTokenProvider.createAccessToken(
                user.getUserId(),
                user.getEmail(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }

    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        user.logout(); //refreshToken 삭제
    }
}
