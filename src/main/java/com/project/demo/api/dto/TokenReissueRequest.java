package com.project.demo.api.dto;

import lombok.Getter;

@Getter
public class TokenReissueRequest {
    private String refreshToken;
}
