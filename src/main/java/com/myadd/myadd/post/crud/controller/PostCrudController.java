package com.myadd.myadd.post.crud.controller;

import com.myadd.myadd.fileUpload.service.FileUploadService;
import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.crud.service.PostCrudService;
import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.security.service.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostCrudController {

    private final PostCrudService postCrudService;
    @Autowired
    private final FileUploadService fileUploadService;
    //포토카드 작성 multipartFile 사용시 RequestPart 사용해야함.
    @ResponseBody
    @PostMapping(value = "/posts/add-post",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<PostBackDto> create(@RequestPart PostBackDto post, @RequestPart(value = "image", required = false)MultipartFile image) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId();

        String S3FileName = fileUploadService.upload(image);
        postCrudService.savePost(post, S3FileName, id);

        return new BaseResponse<>(post, BaseResponseStatus.SUCCESS_CREATE_POST);
    }


    //포토카드 삭제
    @DeleteMapping(value = "/posts/delete-post")
    public BaseResponse<PostBackDto> delete(@RequestParam("postId") Long id) {
       if (!postCrudService.deletePost(id)) {
           return new BaseResponse<>(BaseResponseStatus.FAILED_ALREADY_DELETE_POST);
       }

       return new BaseResponse<>(BaseResponseStatus.SUCCESS_DELETE_POST);
    }


    //포토카드 수정
    @PutMapping(value = "/posts/update-post",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<PostBackDto> update(@RequestParam("postId") Long postId,@Valid @RequestPart PostBackDto post, @RequestPart(value = "image", required = false)MultipartFile image) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId();

        PostEntity postEntity = postCrudService.modifyPost(postId, post, image, id);
        if(postEntity == null) {
            return new BaseResponse<>(BaseResponseStatus.FAILED_CHANGE_POST);
        }

        return new BaseResponse<>(BaseResponseStatus.SUCCESS_CHANGE_POST);
    }
}
