package com.myadd.myadd.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myadd.myadd.post.search.dto.PostSearchDto;
import com.myadd.myadd.user.domain.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Post")
public class PostEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    //@JsonIgnore 이걸 붙이면 에러 해결할 수 있는데 일단 DTO로 해결..
    @ManyToOne @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(nullable = false)
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    @Column(nullable = false)
    private LocalDateTime started_at;
    @Column(nullable = false)
    private LocalDateTime ended_at;
    @Column(nullable = false)
    private String comment; // 필수여부?
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String memo; // 필수여부?
    private String image; // null이면 기본 이미지 보여지게끔
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Long views;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private int platform;
    @Column(nullable = false)
    private int emoji;

    public PostSearchDto toPostSearchDto(PostEntity post){
        PostSearchDto postSearchDto = new PostSearchDto();
        postSearchDto.setPost_id(post.getPost_id());
        postSearchDto.setCreated_at(post.getCreated_at());
        postSearchDto.setModified_at(post.getModified_at());
        postSearchDto.setStarted_at(post.getStarted_at());
        postSearchDto.setEnded_at(post.getEnded_at());
        postSearchDto.setComment(post.getComment());
        postSearchDto.setTitle(post.getTitle());
        postSearchDto.setMemo(post.getMemo());
        postSearchDto.setImage(post.getImage());
        postSearchDto.setCategory(post.getCategory());
        postSearchDto.setViews(post.getViews());
        postSearchDto.setGenre(post.getGenre());
        postSearchDto.setPlatform(post.getPlatform());
        postSearchDto.setEmoji(post.getEmoji());
        return postSearchDto;
    }
}
