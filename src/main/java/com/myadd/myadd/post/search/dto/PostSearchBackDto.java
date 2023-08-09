package com.myadd.myadd.post.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PostSearchBackDto {
    private String title;
    private String emoji;
    private String platform;
    private String genre;
    private LocalDateTime createdAt;
    private String startedAt;
    private String endedAt;
    private Long views;
    private String memo; // 필수여부?

}
