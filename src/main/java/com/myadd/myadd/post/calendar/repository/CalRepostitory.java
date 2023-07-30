package com.myadd.myadd.post.calendar.repository;

import com.myadd.myadd.post.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalRepostitory extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE date_format(p.createdAt, '%Y-%m-%d') = :createdAt and p.user.userId = :userId")
    List<PostEntity> findByCreatedAt(@Param("userId") Long userId, @Param("createdAt") String createdAt);
}
