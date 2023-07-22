package com.myadd.myadd.post.crud.dto;

import com.myadd.myadd.post.domain.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CrudDto {
    private Long post_id;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private LocalDateTime started_at;
    private LocalDateTime ended_at;
    private String comment; // 필수여부?
    private String title;
    private String memo; // 필수여부?
    private String image; // null이면 기본 이미지 보여지게끔
    private String category;
    private Long views;
    private String genre;
    private int platform;
    private int emoji;

    public PostEntity toPostEntity(CrudDto crudDto){
        PostEntity post = new PostEntity();
        post.setPost_id(crudDto.getPost_id());
        post.setCreated_at(LocalDateTime.now());
        post.setModified_at(LocalDateTime.now());
        post.setStarted_at(LocalDateTime.now());
        post.setEnded_at(LocalDateTime.now());
        post.setComment(crudDto.getComment());
        post.setTitle(crudDto.getTitle());
        post.setMemo(crudDto.getMemo());
        post.setImage(crudDto.getImage());
        post.setCategory(crudDto.getCategory());
        post.setViews(0L);
        post.setGenre(crudDto.getGenre());
        post.setPlatform(1);
        post.setEmoji(1);
        return post;
    }
}
