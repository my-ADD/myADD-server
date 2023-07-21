package com.myadd.myadd.post.search.dto;

import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PostSearchDto {
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

    public PostEntity toPostEntity(PostSearchDto postSearchDto){
        PostEntity post = new PostEntity();
        post.setPost_id(postSearchDto.getPost_id());
        post.setCreated_at(postSearchDto.getCreated_at());
        post.setModified_at(postSearchDto.getModified_at());
        post.setStarted_at(postSearchDto.getStarted_at());
        post.setEnded_at(postSearchDto.getEnded_at());
        post.setComment(postSearchDto.getComment());
        post.setTitle(postSearchDto.getTitle());
        post.setMemo(postSearchDto.getMemo());
        post.setImage(postSearchDto.getImage());
        post.setCategory(postSearchDto.getCategory());
        post.setViews(postSearchDto.getViews());
        post.setGenre(postSearchDto.getGenre());
        post.setPlatform(postSearchDto.getPlatform());
        post.setEmoji(postSearchDto.getEmoji());
        return post;
    }
}
