package com.myadd.myadd.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myadd.myadd.post.search.dto.PostSearchDto;
import com.myadd.myadd.user.domain.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Post")
public class PostEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

    //@JsonIgnore 이걸 붙이면 에러 해결할 수 있는데 일단 DTO로 해결..
    @ManyToOne @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name="modified_at")
    private LocalDateTime modifiedAt;
    @Column(name="started_at",nullable = false)
    private LocalDateTime startedAt;
    @Column(name="ended_at",nullable = false)
    private LocalDateTime endedAt;
    @Column(nullable = false)
    private String comment; // 필수여부?
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String memo; // 필수여부?
    private String image; // null이면 기본 이미지 보여지게끔
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Long views;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private int platform;
    @Column(nullable = false)
    private int emoji;

    public PostSearchDto toPostSearchDto(PostEntity post){
        PostSearchDto postSearchDto = new PostSearchDto();
        postSearchDto.setPostId(post.getPostId());
        postSearchDto.setCreatedAt(post.getCreatedAt());
        postSearchDto.setModifiedAt(post.getModifiedAt());
        postSearchDto.setStartedAt(post.getStartedAt());
        postSearchDto.setEndedAt(post.getEndedAt());
        postSearchDto.setComment(post.getComment());
        postSearchDto.setTitle(post.getTitle());
        postSearchDto.setMemo(post.getMemo());
        postSearchDto.setImage(post.getImage());
        postSearchDto.setCategory(post.getCategory());
        postSearchDto.setViews(post.getViews());
        postSearchDto.setGenre(post.getGenre());
        postSearchDto.setPlatform(post.getPlatform());
        postSearchDto.setEmoji(post.getEmoji());
        return postSearchDto;
    }
}
