package com.myadd.myadd.post.crud.dto;

import com.myadd.myadd.post.domain.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@NoArgsConstructor(force = true)
@Data
@Slf4j
public class PostBackDto {
    private Long postId;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String startedAt;

    private String endedAt;

    private String title;

    private String memo;

    private String category;

    private Long views;

    private String genre;

    private String platform;

    private String emoji;

    private String comment;

    private String image; // null이면 기본 이미지 보여지게끔

    public PostEntity toPostEntity(PostBackDto postBackDto){
        PostEntity post = new PostEntity();

        post.setUserId(postBackDto.getUserId());
        post.setPostId(postBackDto.getPostId());
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        post.setStartedAt(postBackDto.getStartedAt());
        post.setEndedAt(postBackDto.getEndedAt());
        post.setTitle(postBackDto.getTitle());
        post.setMemo(postBackDto.getMemo());
        post.setCategory(postBackDto.getCategory());
        post.setViews(postBackDto.getViews());
        post.setGenre(postBackDto.getGenre());
        post.setPlatform(postBackDto.getPlatform());
        post.setEmoji(postBackDto.getEmoji());
        post.setComment(postBackDto.getComment());
        post.setImage(postBackDto.getImage());
        return post;
    }

    public PostEntity toModPostEntity(PostBackDto postBackDto){
        PostEntity post = new PostEntity();

        post.setUserId(postBackDto.getUserId());
        post.setPostId(postBackDto.getPostId());
        post.setCreatedAt(postBackDto.getCreatedAt());
        post.setModifiedAt(LocalDateTime.now());
        post.setStartedAt(postBackDto.getStartedAt());
        post.setEndedAt(postBackDto.getEndedAt());
        post.setTitle(postBackDto.getTitle());
        post.setMemo(postBackDto.getMemo());
        post.setCategory(postBackDto.getCategory());
        post.setViews(postBackDto.getViews());
        post.setGenre(postBackDto.getGenre());
        post.setPlatform(postBackDto.getPlatform());
        post.setEmoji(postBackDto.getEmoji());
        post.setComment(postBackDto.getComment());
        post.setImage(postBackDto.getImage());
        return post;
    }

}
