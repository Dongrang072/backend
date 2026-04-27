package com.zoopick.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FcmTokenRegistrationRequest {
    @NotBlank
    private String token;
}
