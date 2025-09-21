package com.myadd.myadd.post.service;

import com.myadd.myadd.fileUpload.service.FileUploadService;
import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.post.dto.PostDto;
import com.myadd.myadd.post.dto.PostSearchBackDto;
import com.myadd.myadd.post.dto.PostSearchFrontDto;
import com.myadd.myadd.post.repository.PostRepository;
import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public PostService(PostRepository postRepository, UserRepository userRepository, FileUploadService fileUploadService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
    }

    /** 포스트 저장 */
    public void savePost(PostDto postDto, Long userId) throws IOException {
        postDto.setUserId(userId);
        postRepository.save(postDto.toEntity());
    }

    /** 포스트 삭제 */
    public boolean deletePost(Long postId) {
        PostEntity post = postRepository.findByPostId(postId);
        if (post == null) return false;

        if (post.getImage() != null) {
            fileUploadService.fileDelete(post.getImage().split("/")[3]);
        }
        postRepository.delete(post);
        return true;
    }

    /** 포스트 수정 */
    public PostDto modifyPost(Long postId, PostDto postDto, MultipartFile image, Long userId) throws IOException {
        PostEntity existingPost = postRepository.findByPostId(postId);
        if (existingPost == null) return null;

        if (image != null && !image.isEmpty()) {
            fileUploadService.fileDelete(existingPost.getImage().split("/")[3]);
            postDto.setImage(fileUploadService.upload(image));
        } else {
            postDto.setImage(existingPost.getImage());
        }

        postDto.setPostId(postId);
        postDto.setUserId(userId);
        postDto.setCreatedAt(existingPost.getCreatedAt());

        PostEntity updated = postRepository.save(postDto.toEntity());
        return updated.toDto();
    }


    /** 전체 포스트 조회 (기록순:0, 이름순:1) */
    @Transactional
    public List<PostDto> getPostList(Long userId, int sortFlag) {
        UserEntity user = userRepository.findByUserId(userId);

        List<PostEntity> posts = sortFlag == 0
                ? postRepository.findByUserOrderByCreatedAtDesc(user)
                : postRepository.findByUserOrderByTitle(user);

        return posts.stream()
                .map(PostEntity::toDto)
                .collect(Collectors.toList());
    }

    /** 플랫폼/카테고리 필터 조회 (기록순:0, 이름순:1) */
    @Transactional
    public List<PostDto> getPostListByPlatform(Long userId, int sortFlag, String category, String platform) {
        UserEntity user = userRepository.findByUserId(userId);

        List<PostEntity> posts = sortFlag == 0
                ? postRepository.findByPlatformAndCategoryAndUserOrderByCreatedAtDesc(platform, category, user)
                : postRepository.findByPlatformAndCategoryAndUserOrderByTitle(platform, category, user);

        return posts.stream()
                .map(PostEntity::toDto)
                .collect(Collectors.toList());
    }

    /** 생성 날짜 리스트 조회 */
    @Transactional
    public List<String> getPostCreatedAt(Long userId) {
        UserEntity user = userRepository.findByUserId(userId);
        return postRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(post -> post.getCreatedAt().toString())
                .collect(Collectors.toList());
    }

    /** 단일 포스트 앞 페이지 조회 */
    @Transactional
    public PostSearchFrontDto getFrontPage(Long postId) {
        PostEntity post = postRepository.findByPostId(postId);
        if (post == null) return null;

        return PostSearchFrontDto.builder()
                .comment(post.getComment())
                .image(post.getImage())
                .build();
    }

    /** 단일 포스트 뒷 페이지 조회 */
    @Transactional
    public PostSearchBackDto getBackPage(Long postId) {
        PostEntity post = postRepository.findByPostId(postId);
        if (post == null) return null;

        return PostSearchBackDto.builder()
                .memo(post.getMemo())
                .emoji(post.getEmoji())
                .genre(post.getGenre())
                .platform(post.getPlatform())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .startedAt(post.getStartedAt())
                .endedAt(post.getEndedAt())
                .views(post.getViews())
                .build();
    }

    /** 특정 날짜 포스트 조회 */
    @Transactional
    public List<PostDto> findByCreatedAt(Long userId, String createdAt) {
        List<PostEntity> postEntities = postRepository.findByCreatedAt(userId, createdAt);
        if (postEntities == null || postEntities.isEmpty()) return List.of();

        return postEntities.stream()
                .filter(post -> post.getUserId().equals(userId))
                .map(PostEntity::toDto)
                .collect(Collectors.toList());
    }
}
