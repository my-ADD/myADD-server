package com.myadd.myadd.post.crud.dto;

import com.myadd.myadd.post.domain.PostEntity;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PostFrontDto {

    private String comment;

    private String image; // null이면 기본 이미지 보여지게끔

    public PostEntity toPostEntity(PostFrontDto postFrontDto){
        PostEntity post = new PostEntity();

        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        post.setComment(postFrontDto.getComment());
        post.setImage(postFrontDto.getImage());
        return post;
    }
}
