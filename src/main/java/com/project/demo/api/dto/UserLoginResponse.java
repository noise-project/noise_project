package com.project.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {

    private Long userId;
    private String email;
    private String accessToken;
    private String status;
}
