package com.example.member.service;

import com.example.member.entity.User;
import com.example.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 1. 회원가입 (이메일 인증번호 파라미터 및 로직 제거)
    @Transactional
    public User signup(String email, String password, String nickname) {

        // 계정 중복 확인
        if (userRepository.findBySchoolEmail(email).isPresent()) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }

        // 닉네임 중복 확인
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }

        // 비밀번호 암호화 및 유저 객체 생성 (Builder 사용)
        User user = User.builder()
                .schoolEmail(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();

        return userRepository.save(user);
    }

    // 2. 로그인
    public User login(String email, String password) {
        Optional<User> optionalUser = userRepository.findBySchoolEmail(email);

        // 사용자가 존재하고, 암호화된 비밀번호가 일치하는지 확인
        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return optionalUser.get();
        }
        return null;
    }
}