package com.example.member.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 1. 보안 키 생성 (최신 버전에서는 최소 256비트 이상의 키가 필요합니다)
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 토큰 만료 시간 (예: 24시간)
    private final long expirationTime = 1000L * 60 * 60 * 24;

    // 2. 토큰 생성 (사용자 이메일 기준)
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    // 3. 토큰에서 이메일 추출 (JwtAuthenticationFilter에서 사용)
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // 4. 토큰 유효성 검증 (JwtAuthenticationFilter에서 사용)
    public boolean validateToken(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false; // 만료되었거나 변조된 토큰일 경우 false
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()                 // parserBuilder() 대신 parser() 사용
                .verifyWith(secretKey)       // setSigningKey() 대신 verifyWith() 사용
                .build()
                .parseSignedClaims(token)    // parseClaimsJws() 대신 parseSignedClaims() 사용
                .getPayload();               // getBody() 대신 getPayload() 사용
    }
}