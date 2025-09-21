package com.myadd.myadd.post.domain;

import com.myadd.myadd.post.dto.PostDto;
import com.myadd.myadd.user.domain.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Post")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private Long userId;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="modified_at")
    private LocalDateTime modifiedAt;

    @Column(name="started_at")
    private String startedAt;

    @Column(name="ended_at")
    private String endedAt;

    private String comment;
    private String title;
    private String memo;
    private String image;
    private String category;

    @ColumnDefault("0")
    private Long views;

    private String genre;
    private String platform;
    private String emoji;

    /** PostDto -> PostEntity 변환 */
    public static PostEntity fromDto(PostDto dto) {
        return PostEntity.builder()
                .postId(dto.getPostId())
                .userId(dto.getUserId())
                .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now())
                .modifiedAt(dto.getModifiedAt() != null ? dto.getModifiedAt() : LocalDateTime.now())
                .startedAt(dto.getStartedAt())
                .endedAt(dto.getEndedAt())
                .comment(dto.getComment())
                .title(dto.getTitle())
                .memo(dto.getMemo())
                .image(dto.getImage())
                .category(dto.getCategory())
                .views(dto.getViews() != null ? dto.getViews() : 0)
                .genre(dto.getGenre())
                .platform(dto.getPlatform())
                .emoji(dto.getEmoji())
                .build();
    }

    /** PostEntity -> PostDto 변환 */
    public PostDto toDto() {
        return PostDto.builder()
                .postId(this.postId)
                .userId(this.userId)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .startedAt(this.startedAt)
                .endedAt(this.endedAt)
                .comment(this.comment)
                .title(this.title)
                .memo(this.memo)
                .image(this.image)
                .category(this.category)
                .views(this.views)
                .genre(this.genre)
                .platform(this.platform)
                .emoji(this.emoji)
                .build();
    }
}
