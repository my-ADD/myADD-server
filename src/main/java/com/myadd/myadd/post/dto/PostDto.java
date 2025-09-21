package com.myadd.myadd.post.dto;

import com.myadd.myadd.post.domain.PostEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String startedAt;
    private String endedAt;
    private String title;
    private String memo;
    private String category;
    private Long views;
    private String genre;
    private String platform;
    private String emoji;
    private String comment;
    private String image;

    /** DTO -> Entity 변환 */
    public PostEntity toEntity() {
        return PostEntity.fromDto(this);
    }

    /** Entity -> DTO 변환 */
    public static PostDto fromEntity(PostEntity entity) {
        return entity.toDto();
    }
}
