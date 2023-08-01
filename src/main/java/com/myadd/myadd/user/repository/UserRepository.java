package com.myadd.myadd.user.repository;

import com.myadd.myadd.user.domain.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email); // 이미 생성된 사용자인지 판단
    UserEntity findByUserId(Long userId);
}
