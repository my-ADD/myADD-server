package com.myadd.myadd.post.crud.dto;

import com.myadd.myadd.post.domain.PostEntity;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor(force = true)
@Data
@Slf4j
public class PostBackDto {
    private Long postId;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @NotBlank(message = "필수입력값입니다")
    private String startedAt;

    @NotBlank(message = "필수입력값입니다")
    private String endedAt;

    @NotBlank(message = "필수입력값입니다")
    private String title;

    @NotBlank(message = "필수입력값입니다")
    private String memo;

    @NotBlank(message = "필수입력값입니다")
    private String category;

    private Long views;

    @NotBlank(message = "필수입력값입니다")
    private String genre;

    @NotNull
    private String platform;

    @NotNull
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
