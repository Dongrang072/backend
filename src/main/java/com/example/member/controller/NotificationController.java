package com.example.member.controller;

import com.example.member.dto.CommonResponse;
import com.example.member.dto.FcmNotificationRequest;
import com.example.member.dto.FcmTokenRegistrationRequest;
import com.example.member.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notification API", description = "FCM 토큰을 등록 및 알림 전송")
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "FCM 토큰 등록", description = "클라이언트의 FCM 토큰을 등록합니다.")
    @PostMapping("/api/auth/device-token")
    public ResponseEntity<CommonResponse<String>> registerFcmToken(@RequestHeader(value = "Authorization", defaultValue = "") String sessionToken, @RequestBody FcmTokenRegistrationRequest request) {
        try {
            sessionToken = sessionToken.replace("Bearer ", "");
            notificationService.register(sessionToken, request.getToken());
            return ResponseEntity.ok(CommonResponse.success("FCM 토큰이 등록되었습니다."));
        } catch (IllegalStateException exception) {
            return ResponseEntity.badRequest().body(CommonResponse.error(exception.getMessage()));
        }
    }

    @Operation(summary = "대상에게 알림 전송", description = "클라이언트로 알림을 보냅니다. (ADMIN)")
    @PostMapping("/admin/send/{targetNickname}")
    public ResponseEntity<CommonResponse<String>> sendNotification(@PathVariable("targetNickname") String targetNickname, @RequestBody FcmNotificationRequest request) {
        try {
            String result = notificationService.send(targetNickname, request);
            return ResponseEntity.ok(CommonResponse.success(result));
        } catch (FirebaseMessagingException exception) {
            return ResponseEntity.badRequest().body(CommonResponse.error(exception.getMessage()));
        }
    }
}
