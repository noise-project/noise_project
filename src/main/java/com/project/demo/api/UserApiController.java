package com.project.demo.api;

import com.project.demo.api.dto.UserLoginRequest;
import com.project.demo.api.dto.UserLoginResponse;
import com.project.demo.api.dto.UserSignUpRequest;
import com.project.demo.api.dto.UserSignUpResponse;
import com.project.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUp(@RequestBody @Valid UserSignUpRequest request) {
        Long userId = userService.signUp(request);

        return ResponseEntity.ok(
                new UserSignUpResponse(
                        userId,
                        request.getEmail(),
                        "회원가입이 완료되었습니다."
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) {

        Long userId = userService.login(request);
        return ResponseEntity.ok(
                new UserLoginResponse(
                        userId,
                        request.getEmail(),
                        "로그인에 성공했습니다."
                )
        );
    }

}
