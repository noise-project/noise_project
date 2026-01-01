package com.project.demo.api.dto;

import lombok.Getter;

@Getter
public class UserSignUpRequest {

    private String email;
    private String password;
    private String nickname;

    private Long unitId; //동,호 정보

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getUnitId() {
        return unitId;
    }
}
