package com.myadd.myadd.post.repository;

import com.myadd.myadd.post.domain.PostEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    // 외래키와 자동으로 연결되는 걸 보면 그냥 user_id로 유저 찾아서 그 유저의 post를 긁어오는 게 더 간편할 거 같다!

    // 포토카드 전체 목록 조회(기록순)
    List<PostEntity> findAllByOrderByCreatedAtDesc();
    // 포토카드 전체 목록 조회(이름순)
    List<PostEntity> findAllByOrderByTitle();
    // 포토카드 하나 조회
    PostEntity findByPostId(Long postId);
}
