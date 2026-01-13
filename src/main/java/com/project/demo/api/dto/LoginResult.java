package com.project.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResult {

    private UserLoginResponse response;
    private String refreshToken;
}
