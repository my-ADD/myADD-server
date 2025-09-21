package com.myadd.myadd.post.controller;

import com.myadd.myadd.post.dto.PostDto;
import com.myadd.myadd.post.dto.PostSearchBackDto;
import com.myadd.myadd.post.dto.PostSearchFrontDto;
import com.myadd.myadd.post.service.PostService;
import com.myadd.myadd.fileUpload.service.FileUploadService;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final FileUploadService fileUploadService;

    private Long getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) return null;
        return ((CustomUserDetails) auth.getPrincipal()).getId();
    }

    @PostMapping(value = "/add-post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<PostDto> createPost(@RequestPart PostDto postDto, @RequestPart(required = false) MultipartFile image) throws IOException {
        Long userId = getAuthenticatedUserId();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        if(image != null && !image.isEmpty()){
            String storedFile = fileUploadService.upload(image);
            postDto.setImage(storedFile);
        }

        postService.savePost(postDto, userId);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS_CREATE_POST);
    }

    @DeleteMapping("/delete-post")
    public BaseResponse<PostDto> deletePost(@RequestParam Long postId){
        boolean deleted = postService.deletePost(postId);
        return deleted
                ? new BaseResponse<>(BaseResponseStatus.SUCCESS_DELETE_POST)
                : new BaseResponse<>(BaseResponseStatus.FAILED_ALREADY_DELETE_POST);
    }

    @PutMapping(value = "/update-post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<PostDto> updatePost(@RequestParam Long postId, @RequestPart PostDto postDto, @RequestPart(required = false) MultipartFile image) throws IOException {
        Long userId = getAuthenticatedUserId();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        PostDto updatedPost = postService.modifyPost(postId, postDto, image, userId);
        if(updatedPost == null) return new BaseResponse<>(BaseResponseStatus.FAILED_CHANGE_POST);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS_CHANGE_POST);
    }

    @GetMapping("/createdAt")
    public BaseResponse<List<String>> getCreatedDates() {
        Long userId = getAuthenticatedUserId();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        return new BaseResponse<>(postService.getPostCreatedAt(userId), BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/get-post-list-all")
    public BaseResponse<List<PostDto>> getAllPosts(@RequestParam(defaultValue = "createdAt") String sortBy) {
        Long userId = getAuthenticatedUserId();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        int flag = "title".equalsIgnoreCase(sortBy) ? 1 : 0;
        return new BaseResponse<>(postService.getPostList(userId, flag), BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/get-post/front")
    public BaseResponse<PostSearchFrontDto> getFrontPost(@RequestParam Long postId){
        if(postId == null) return new BaseResponse<>(BaseResponseStatus.POST_SEARCH_EMPTY_POSTID);

        PostSearchFrontDto front = postService.getFrontPage(postId);
        return front == null
                ? new BaseResponse<>(BaseResponseStatus.GET_POST_NOT_EXISTS)
                : new BaseResponse<>(front, BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/get-post/back")
    public BaseResponse<PostSearchBackDto> getBackPost(@RequestParam Long postId){
        if(postId == null) return new BaseResponse<>(BaseResponseStatus.POST_SEARCH_EMPTY_POSTID);

        PostSearchBackDto back = postService.getBackPage(postId);
        return back == null
                ? new BaseResponse<>(BaseResponseStatus.GET_POST_NOT_EXISTS)
                : new BaseResponse<>(back, BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/get-post-list")
    public BaseResponse<List<PostDto>> getPostsByPlatform(
            @RequestParam String category,
            @RequestParam String platform,
            @RequestParam(defaultValue = "createdAt") String sortBy) {

        Long userId = getAuthenticatedUserId();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);
        if(category == null || platform == null) return new BaseResponse<>(BaseResponseStatus.FAILED_INVALID_INPUT);

        int flag = "title".equalsIgnoreCase(sortBy) ? 1 : 0;
        return new BaseResponse<>(postService.getPostListByPlatform(userId, flag, category, platform), BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/calendar")
    public BaseResponse<List<PostDto>> getPostsByDate(@RequestParam String createdAt){
        Long userId = getAuthenticatedUserId();
        if(userId == null) return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        List<PostDto> posts = postService.findByCreatedAt(userId, createdAt);
        BaseResponseStatus status = posts.isEmpty() ? BaseResponseStatus.SUCCESS_CALENDAR_POST : BaseResponseStatus.SUCCESS;

        return new BaseResponse<>(posts, status);
    }
}
