package com.example.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuth {

    @Id
    private String email;

    private String certificationNumber;

    private boolean isVerified = false;
}