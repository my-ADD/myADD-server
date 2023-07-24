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
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String comment; // 필수여부?
    private String title;
    private String memo; // 필수여부?
    private String image; // null이면 기본 이미지 보여지게끔
    private String category;
    private Long views;
    private String genre;
    private int platform;
    private int emoji;

    // 없어도 상관없을 거 같은데 일단 보류
    public PostEntity toPostEntity(PostSearchDto postSearchDto){
        PostEntity post = new PostEntity();
        post.setPostId(postSearchDto.getPostId());
        post.setCreatedAt(postSearchDto.getCreatedAt());
        post.setModifiedAt(postSearchDto.getModifiedAt());
        post.setStartedAt(postSearchDto.getStartedAt());
        post.setEndedAt(postSearchDto.getEndedAt());
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
