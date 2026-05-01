package com.zoopick.server.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResult {
    private String accessToken;
    private String nickname;
    private String department;
    private String grade;
    private String message;
}
