package com.myadd.myadd.post.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @NonNull
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    @NonNull
    private LocalDateTime started_at;
    @NonNull
    private LocalDateTime ended_at;
    @NonNull
    private String comment; // 필수여부?
    @NonNull
    private String title;
    @NonNull
    private String memo; // 필수여부?
    private String image; // null이면 기본 이미지 보여지게끔
    @NonNull
    private String category;
    @NonNull
    private Long views;
    @NonNull
    private String genre;
    @NonNull
    private int platform;
    @NonNull
    private int emoji;
}
