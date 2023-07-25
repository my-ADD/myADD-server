package com.myadd.myadd.post.crud.dto;

import com.myadd.myadd.post.domain.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PostCrudDto {
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String startedAt;
    private String endedAt;
    private String comment; // 필수여부?
    private String title;
    private String memo; // 필수여부?
    private String image; // null이면 기본 이미지 보여지게끔
    private String category;
    private Long views;
    private String genre;
    private int platform;
    private int emoji;

    public String toString() {
        return postId + " " + category;
    }

    public PostEntity toPostEntity(PostCrudDto postCrudDto){
        PostEntity post = new PostEntity();
        post.setPostId(postCrudDto.getPostId());
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        post.setStartedAt(postCrudDto.getStartedAt());
        post.setEndedAt(postCrudDto.getEndedAt());
        post.setComment(postCrudDto.getComment());
        post.setTitle(postCrudDto.getTitle());
        post.setMemo(postCrudDto.getMemo());
        post.setImage(postCrudDto.getImage());
        post.setCategory(postCrudDto.getCategory());
        post.setViews(postCrudDto.getViews());
        post.setGenre(postCrudDto.getGenre());
        post.setPlatform(postCrudDto.getPlatform());
        post.setEmoji(postCrudDto.getEmoji());
        return post;
    }
}
