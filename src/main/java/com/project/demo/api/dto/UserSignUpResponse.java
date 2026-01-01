package com.project.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class UserSignUpResponse {

    private Long userId;
    private String email;
    private String message;
}
