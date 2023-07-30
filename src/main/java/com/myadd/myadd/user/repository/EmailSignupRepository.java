package com.myadd.myadd.user.repository;

import com.myadd.myadd.user.sigunup.email.auth.domain.EmailSignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailSignupRepository extends JpaRepository<EmailSignupEntity, Long> {
    Optional<EmailSignupEntity> findByEmail(String email);
}
