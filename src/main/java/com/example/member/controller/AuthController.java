package com.example.member.controller;

import com.example.member.dto.CommonResponse;
import com.example.member.dto.SignupRequest;
import com.example.member.entity.User;
import com.example.member.service.AuthService;
import com.example.member.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // 1. 회원가입
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Map<String, Object>>> signup(@RequestBody SignupRequest request) {
        try {
            User savedUser = authService.signup(
                    request.getSchoolEmail(),
                    request.getPassword(),
                    request.getNickname()
            );

            Map<String, Object> result = new HashMap<>();
            result.put("user_id", savedUser.getId());
            result.put("message", "회원가입 성공!");

            return ResponseEntity.status(201).body(CommonResponse.success(result));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(CommonResponse.error(e.getMessage()));
        }
    }

    // 2. 로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Map<String, Object>>> login(@RequestBody SignupRequest request) {
        User user = authService.login(request.getSchoolEmail(), request.getPassword());

        if (user != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("token", jwtUtil.generateToken(user.getSchoolEmail()));
            result.put("nickname", user.getNickname());
            result.put("message", "로그인 성공");

            return ResponseEntity.ok(CommonResponse.success(result));
        }
        return ResponseEntity.status(401).body(CommonResponse.error("로그인 정보가 틀렸습니다."));
    }
}