package com.project.demo.service;

import com.project.demo.api.dto.UserLoginRequest;
import com.project.demo.api.dto.UserLoginResponse;
import com.project.demo.api.dto.UserSignUpRequest;
import com.project.demo.domain.unit.Unit;
import com.project.demo.domain.user.Status;
import com.project.demo.domain.user.User;
import com.project.demo.jwt.JwtTokenProvider;
import com.project.demo.repository.UnitRepository;
import com.project.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUp(UserSignUpRequest request) {

        // 이메일 중복 체크
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }
        // Unit 조회
        Unit unit = unitRepository.findById(request.getUnitId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 세대입니다."));

        // User 생성
        User user = User.create(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()), //암호화
                request.getNickname(),
                unit,
                Status.PENDING
        );

        // 저장
        userRepository.save(user);
        return user.getUserId();
    }
}
