package com.zoopick.server.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zoopick.server.entity.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 알림 하나의 데이터
 */
@Getter
@Setter
public class NotificationRecord {
    private long id;
    private NotificationType type;
    private Map<String, Object> payload;
    @JsonProperty("read_at")
    private LocalDateTime readAt;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
