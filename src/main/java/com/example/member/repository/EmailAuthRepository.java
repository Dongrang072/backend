package com.example.member.repository;

import com.example.member.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth, String> {
}