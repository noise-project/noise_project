package com.project.demo.api;

import com.project.demo.api.dto.*;
import com.project.demo.domain.user.Status;
import com.project.demo.service.AuthService;
import com.project.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUp(@RequestBody @Valid UserSignUpRequest request) {
        Long userId = userService.signUp(request);
        return ResponseEntity.ok(
                new UserSignUpResponse(
                        userId,
                        request.getEmail(),
                        Status.PENDING.name(),
                        "회원가입 완료, 관리자 승인 대기중"
                )
        );
    }
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request, HttpServletResponse response) {
        LoginResult result = authService.login(request);

        Cookie cookie = new Cookie("refreshToken", result.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(14 * 24 * 60 * 60);

        response.addCookie(cookie);

        return ResponseEntity.ok(result.getResponse());

    }

}
