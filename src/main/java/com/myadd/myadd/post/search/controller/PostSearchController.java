package com.myadd.myadd.post.search.controller;

import com.myadd.myadd.post.search.dto.PostSearchBackDto;
import com.myadd.myadd.post.search.dto.PostSearchDto;
import com.myadd.myadd.post.search.dto.PostSearchFrontDto;
import com.myadd.myadd.post.search.service.PostSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostSearchController {
    private PostSearchService postSearchService;
    public PostSearchController(PostSearchService postSearchService){
        this.postSearchService = postSearchService;
    }
    /**
    특정 유저의 포토카드 전체 목록 조회 API(기록순)
     [GET] /posts/get-post-list/:user_id/createdAt
     */
    //PathVariable
    @GetMapping("/posts/get-post-list/{user_id}/createdAt")
    @ResponseBody
    public List<PostSearchDto> postListByCreatedAt(@PathVariable("user_id") Long userId, Model model){
        List<PostSearchDto> postSearchDtoList = postSearchService.getPostList(userId,0);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }
    /**
     특정 유저의 포토카드 전체 목록 조회 API(이름순)
     [GET] /posts/get-post-list/:user_id/title
     */
    //PathVariable
    @GetMapping("/posts/get-post-list/{user_id}/title")
    @ResponseBody
    public List<PostSearchDto> postListByTitle(@PathVariable("user_id") Long userId, Model model){
        List<PostSearchDto> postSearchDtoList = postSearchService.getPostList(userId,1);
        model.addAttribute("postList",postSearchDtoList);

        return postSearchDtoList;
    }

    /**
     포토 카드 하나의 앞장 상세 정보 조회
     [GET] /posts/get-post/:post_id/front
     */
    @GetMapping("/posts/get-post/{post_id}/front")
    @ResponseBody
    public PostSearchFrontDto frontPage(@PathVariable("post_id") Long postId,Model model){
        PostSearchFrontDto postSearchFrontDto = postSearchService.getFrontPage(postId);

        return postSearchFrontDto;
    }

    /**
     포토 카드 하나의 뒷장 상세 정보 조회
     [GET] /posts/get-post/:post_id/back
     */
    @GetMapping("/posts/get-post/{post_id}/back")
    @ResponseBody
    public PostSearchBackDto backPage(@PathVariable("post_id") Long postId,Model model){
        PostSearchBackDto postSearchBackDto = postSearchService.getBackPage(postId);

        return postSearchBackDto;
    }
}
