package com.zoopick.server.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.zoopick.server.dto.notification.NotificationResponse;
import com.zoopick.server.dto.notification.SimpleNotificationRequest;
import com.zoopick.server.entity.User;
import com.zoopick.server.entity.ZoopickNotification;
import com.zoopick.server.exception.AccessTokenException;
import com.zoopick.server.exception.DataNotFoundException;
import com.zoopick.server.mapper.NotificationMapper;
import com.zoopick.server.repository.NotificationRepository;
import com.zoopick.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@NullMarked
public class NotificationService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public void register(String email, String fcmToken) throws AccessTokenException, DataNotFoundException {
        User user = userRepository.findBySchoolEmail(email)
                .orElseThrow(() -> DataNotFoundException.from("사용자", email));
        user.setFcmToken(fcmToken);
        userRepository.save(user);
    }

    public String send(String targetNickname, SimpleNotificationRequest request)
            throws DataNotFoundException, FirebaseMessagingException {
        User user = userRepository.findByNickname(targetNickname)
                .orElseThrow(() -> DataNotFoundException.from("사용자", targetNickname));
        String fcmToken = user.getFcmToken();
        if (fcmToken == null)
            throw DataNotFoundException.from("FCM 토큰", user.getSchoolEmail());
        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .build();
        Message message = Message.builder()
                .setNotification(notification)
                .putAllData(request.getData())
                .setToken(fcmToken)
                .build();
        return FirebaseMessaging.getInstance().send(message);
    }

    public List<NotificationResponse.Data> getNotifications(String email) {
        return findNotificationsWith(email, notificationRepository::findByUserIdOrderByCreatedAtDesc);
    }

    public List<NotificationResponse.Data> getUnreadNotifications(String email) {
        return findNotificationsWith(email, notificationRepository::findByUserIdAndReadAtIsNullOrderByCreatedAtDesc);
    }

    private List<NotificationResponse.Data> findNotificationsWith(String email, Function<Long, List<ZoopickNotification>> repositoryAccessor) {
        User user = userRepository.findBySchoolEmail(email)
                .orElseThrow(() -> DataNotFoundException.from("사용자", email));

        List<ZoopickNotification> notifications = repositoryAccessor.apply(user.getId());
        return notifications.stream()
                .map(notificationMapper::toNotificationResponse)
                .toList();
    }
}
