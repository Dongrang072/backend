package com.zoopick.server.dto.notification;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SimpleNotificationRequest {
    private String title;
    private String body;
    private Map<String, String> data = Map.of();
}
