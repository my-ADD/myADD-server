package com.myadd.myadd.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myadd.myadd.user.domain.UserEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Post")
public class PostEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

    @JsonIgnore //이걸 붙이면 에러 해결할 수 있는데 일단 DTO로 해결..
    @ManyToOne @JoinColumn(name="user_id")
    private UserEntity user;

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

    private int platform;

    private int emoji;

}