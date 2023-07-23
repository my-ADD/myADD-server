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
    private Long post_id;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private String started_at;
    private String ended_at;
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
        return post_id + " " + category;
    }

    public PostEntity toPostEntity(PostCrudDto postCrudDto){
        PostEntity post = new PostEntity();
        post.setPost_id(postCrudDto.getPost_id());
        post.setCreated_at(LocalDateTime.now());
        post.setModified_at(LocalDateTime.now());
        post.setStarted_at(postCrudDto.getStarted_at());
        post.setEnded_at(postCrudDto.getEnded_at());
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
