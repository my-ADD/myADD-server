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
    /* 유저명에 따른 게시글을 불러오고 싶은데 잘 안된다..
    @Query(value = "select p from PostEntity p where user = user;")
    List<PostEntity> findByUser_id(User user);
     */
}
