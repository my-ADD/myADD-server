package com.myadd.myadd.post.crud.controller;

import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.post.crud.service.PostCrudService;
import com.myadd.myadd.user.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostCrudController {

    private final PostCrudService postCrudService;

    //포토카드 작성 multipartFile 사용시 RequestPart 사용해야함.
    @PostMapping(value = "/posts/add-post",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody // 대신 @RequestPart 로 적기
    public PostBackDto create(@RequestPart PostBackDto post, @RequestPart(value = "imageURL", required = false)MultipartFile imageURL) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId();
        log.info(String.valueOf(imageURL));
        postCrudService.savePost(post, imageURL, id);
        return post;
    }


    //포토카드 삭제
    @ResponseBody
    @DeleteMapping(value = "/posts/delete-post")
    public String delete(@RequestParam("postId") Long id) {
       postCrudService.deletePost(id);

       return id + "번 삭제 완료";
    }


    //포토카드 수정
    @PutMapping(value = "/posts/update-post",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody // 대신 @RequestPart 로 적기
    public String update(@RequestParam("postId") Long postId, @RequestPart PostBackDto post, @RequestPart(value = "imageURL", required = false)MultipartFile imageURL) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getId();
        log.info(String.valueOf(id));
        postCrudService.modifyPost(postId, post, imageURL, id);
        return "수정 완료";
    }
}
