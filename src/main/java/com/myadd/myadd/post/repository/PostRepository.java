package com.myadd.myadd.post.repository;

import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.user.domain.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    // 전체조회 시 And 조건을 이용하여 User까지 긁어오면 더 코드가 간편해질거같다!
    // 사용자의 포토카드 전체 리스트 조회
    Page<PostEntity> findByUser(UserEntity user, Pageable pageable);
    // 포토카드 플랫폼에 따른 목록 조회(기록순)
    Page<PostEntity> findByPlatformAndCategoryAndUserOrderByCreatedAtDesc(String platform, String category, UserEntity user, Pageable pageable);
    // 포토카드 플랫폼에 따른 목록 조회(이름순)
    Page<PostEntity> findByPlatformAndCategoryAndUserOrderByTitle(String platform,String category,UserEntity user,Pageable pageable);
    // 포토카드 하나 조회
    PostEntity findByPostId(Long postId);
}
