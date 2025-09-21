package com.myadd.myadd.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostSearchBackDto {
    private String title;
    private String emoji;
    private String platform;
    private String genre;
    private LocalDateTime createdAt;
    private String startedAt;
    private String endedAt;
    private Long views;
    private String memo; // 필수 여부는 비즈니스 로직에 따라 결정
}
