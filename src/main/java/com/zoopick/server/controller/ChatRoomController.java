package com.zoopick.server.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.zoopick.server.dto.CommonResponse;
import com.zoopick.server.dto.chat.*;
import com.zoopick.server.service.ChatRoomService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-rooms")
@NullMarked
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/")
    public ResponseEntity<CommonResponse<ListChatRoomResult>> getChatRooms(Authentication authentication) {
        String email = authentication.getName();
        ListChatRoomResult result = chatRoomService.getChatRooms(email);
        return ResponseEntity.ok(CommonResponse.success(result));
    }

    @PostMapping("/create")
    public ResponseEntity<CommonResponse<CreateChatRoomResult>> createChatRoom(
            Authentication authentication,
            @RequestBody @Valid CreateChatRoomRequest createChatRoomRequest
    ) {
        String email = authentication.getName();
        CreateChatRoomResult result = chatRoomService.createChatRoom(email, createChatRoomRequest);
        return ResponseEntity.ok(CommonResponse.success(result));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<CommonResponse<ChatRoomRecord>> getChatRoom(
            Authentication authentication,
            @PathVariable long roomId
    ) {
        String email = authentication.getName();
        ChatRoomRecord record = chatRoomService.getChatRoom(email, roomId);
        return ResponseEntity.ok(CommonResponse.success(record));
    }

    @RequestMapping(value = "/{roomId}/messages", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<CommonResponse<ListMessagesResult>> getChatRoomMessages(
            Authentication authentication,
            @PathVariable long roomId,
            @RequestBody(required = false) MessageFilter messageFilter
    ) {
        String email = authentication.getName();
        ListMessagesResult result = chatRoomService.getMessages(email, roomId, messageFilter);
        return ResponseEntity.ok(CommonResponse.success(result));
    }

    @PostMapping("/{roomId}/messages/send")
    public ResponseEntity<CommonResponse<String>> sendMessage(
            Authentication authentication,
            @PathVariable long roomId,
            @RequestBody @Valid SendMessageRequest sendMessageRequest
    ) throws FirebaseMessagingException {
        String email = authentication.getName();
        chatRoomService.sendMessage(email, roomId, sendMessageRequest.getMessage());
        return ResponseEntity.ok(CommonResponse.success("done"));
    }
}
