package com.example.member.controller;

import com.example.member.dto.CheckCertificationRequest;
import com.example.member.dto.CommonResponse;
import com.example.member.dto.EmailcertificationRequest;
import com.example.member.dto.SignupRequest;
import com.example.member.entity.User;
import com.example.member.service.AuthService;
import com.example.member.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Auth API", description = "학생 이메일 인증 및 회원가입/로그인 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "1. 회원가입", description = "서버에 이메일 인증이 완료된 상태인지 확인 후 가입을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 오류 또는 미인증 이메일")
    })
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Map<String, Object>>> signup(@RequestBody SignupRequest request) {
        try {
            // 💡 authCode 없이 이메일, 비밀번호, 닉네임만 전달 (DB의 isVerified 상태로 검증)
            User savedUser = authService.signup(
                    request.getSchoolEmail(),
                    request.getPassword(),
                    request.getNickname()
            );

            Map<String, Object> result = new HashMap<>();
            result.put("user_id", savedUser.getId());
            result.put("message", "회원가입이 완료되었습니다.");

            return ResponseEntity.status(201).body(CommonResponse.success(result));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(CommonResponse.error(e.getMessage()));
        }
    }

    @Operation(summary = "2. 로그인", description = "이메일과 비밀번호로 로그인을 진행하고 JWT 토큰을 반환받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 및 토큰 발급"),
            @ApiResponse(responseCode = "401", description = "로그인 정보 불일치")
    })
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Map<String, Object>>> login(@RequestBody SignupRequest request) {
        try {
            User user = authService.login(request.getSchoolEmail(), request.getPassword());

            if (user != null) {
                Map<String, Object> result = new HashMap<>();
                result.put("token", jwtUtil.generateToken(user.getSchoolEmail()));
                result.put("nickname", user.getNickname());
                result.put("message", "로그인 성공");

                return ResponseEntity.ok(CommonResponse.success(result));
            }
            return ResponseEntity.status(401).body(CommonResponse.error("로그인 정보가 틀렸습니다."));
        } catch (RuntimeException e) {
            // AuthService 내부 에러 발생 시 처리
            return ResponseEntity.status(401).body(CommonResponse.error(e.getMessage()));
        }
    }

    @Operation(summary = "3. 이메일 인증 번호 발송", description = "가입하려는 이메일로 인증 코드를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드 발송 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 이메일 형식 또는 발송 실패")
    })
    @PostMapping("/certification")
    public ResponseEntity<CommonResponse<String>> sendCertificationEmail(@RequestBody @Valid EmailcertificationRequest request) {
        try {
            authService.sendCertificationEmail(request);
            return ResponseEntity.ok(CommonResponse.success("인증 코드가 발송되었습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(CommonResponse.error(e.getMessage()));
        }
    }

    @Operation(summary = "4. 인증 번호 확인 (검증)", description = "이메일로 발송된 6자리 인증 번호가 맞는지 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 성공"),
            @ApiResponse(responseCode = "400", description = "인증번호 불일치 또는 만료")
    })
    @PostMapping("/verify")
    public ResponseEntity<CommonResponse<String>> verifyCertificationCode(@RequestBody @Valid CheckCertificationRequest request) {
        try {
            authService.verifyCertificationCode(request);
            return ResponseEntity.ok(CommonResponse.success("인증에 성공하였습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(CommonResponse.error(e.getMessage()));
        }
    }
}