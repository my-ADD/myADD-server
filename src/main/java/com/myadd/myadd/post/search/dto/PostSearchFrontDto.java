package com.myadd.myadd.post.search.dto;

import com.myadd.myadd.post.domain.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostSearchFrontDto {
    private String image;
    private String comment;
}
