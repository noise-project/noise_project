package com.project.demo.api.dto;

import com.project.demo.domain.user.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PendingUserResponse {
    private Long userId;
    private String email;
    private String nickname;
    private String unitName;
    private Status status;
}
