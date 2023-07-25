package com.myadd.myadd.post.crud.repository;

import com.myadd.myadd.post.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostCrudRepository extends JpaRepository<PostEntity, Long> {

    PostEntity findByPostId(Long postId);
}
