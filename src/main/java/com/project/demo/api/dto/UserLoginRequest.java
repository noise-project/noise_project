package com.project.demo.api.dto;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
