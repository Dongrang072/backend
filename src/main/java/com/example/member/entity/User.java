package com.example.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter // 서비스 로직에서 값을 채워넣기 위해 클래스 레벨에 Setter 허용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String schoolEmail;

    @Column
    private String password;

    @Column
    private String nickname;

}