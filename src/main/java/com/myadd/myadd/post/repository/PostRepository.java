package com.myadd.myadd.post.repository;

import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    // 전체조회 시 And 조건을 이용하여 User까지 긁어오면 더 코드가 간편해질거같다!
    // 사용자의 포토카드 전체 리스트 조회(기록순)
    List<PostEntity> findByUserOrderByCreatedAtDesc(UserEntity user);
    // 사용자의 포토카드 전체 리스트 조회(이름순)
    List<PostEntity> findByUserOrderByTitle(UserEntity user);
    // 포토카드 플랫폼에 따른 목록 조회(기록순)
    List<PostEntity> findByPlatformAndCategoryAndUserOrderByCreatedAtDesc(String platform, String category, UserEntity user);
    // 포토카드 플랫폼에 따른 목록 조회(이름순)
    List<PostEntity> findByPlatformAndCategoryAndUserOrderByTitle(String platform,String category,UserEntity user);
    // 포토카드 하나 조회
    PostEntity findByPostId(Long postId);

    @Query("SELECT p FROM PostEntity p WHERE date_format(p.createdAt, '%Y-%m-%d') = :createdAt and p.user.userId = :userId")
    List<PostEntity> findByCreatedAt(@Param("userId") Long userId, @Param("createdAt") String createdAt);
}
