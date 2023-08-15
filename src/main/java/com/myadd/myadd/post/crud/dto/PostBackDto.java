package com.myadd.myadd.post.crud.dto;

import com.myadd.myadd.post.domain.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor(force = true)
@Data
@Slf4j
public class PostBackDto {
    private Long postId;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @NotNull(message = "시청한 시작일을 입력해주세요")
    private String startedAt;

    @NotNull (message = "시청한 마지막날을 입력해주세요")
    private String endedAt;

    @NotNull(message = "제목을 입력해주세요")
    private String title;

    @NotNull(message = "메모를 입력해주세요")
    private String memo;

    @NotNull(message = "카테고리를 입력해주세요")
    private String category;

    @NotNull(message = "시청횟수를 입력해주세요")
    private Long views;

    @NotNull(message = "장르를 입력해주세요")
    private String genre;

    @NotNull(message = "플랫폼을 선택해주세요")
    private String platform;

    @NotNull(message = "이모지를 입력해주세요")
    private String emoji;

    @NotNull(message = "코멘트를 입력해주세요")
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
