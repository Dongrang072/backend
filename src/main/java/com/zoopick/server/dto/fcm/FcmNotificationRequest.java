package com.zoopick.server.dto.fcm;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FcmNotificationRequest {
    @NotBlank
    private String targetEmail;
    private String title;
    private String body;
    private Map<String, String> data = Map.of();
}
