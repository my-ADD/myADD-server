package com.myadd.myadd.user.repository;

import com.myadd.myadd.user.sigunup.email.authcode.domain.EmailAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailSignupRepository extends JpaRepository<EmailAuthEntity, Long> {
    Optional<EmailAuthEntity> findByEmail(String email);
}
